/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package v1r2.services

import support.UnitSpec
import uk.gov.hmrc.domain.Nino
import uk.gov.hmrc.http.HeaderCarrier
import v1r2.controllers.EndpointLogContext
import v1r2.fixtures.common.MessageFixtures._
import v1r2.fixtures.getCalculation.GetCalculationResponseFixtures._
import v1r2.mocks.connectors.MockTaxCalcConnector
import v1r2.models.domain.{CalculationReason, CalculationRequestor, CalculationType}
import v1r2.models.errors._
import v1r2.fixtures.getCalculation.endOfYearEstimate.EoyEstimateFixtures.eoyEstimateResponse

import v1r2.models.outcomes.ResponseWrapper
import v1r2.models.request.getCalculation.GetCalculationRequest
import v1r2.models.response.common.{Messages, Metadata}
import v1r2.models.response.getCalculation.{GetCalculationResponse, MetadataExistence}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GetCalculationServiceSpec extends UnitSpec {

  val metadataResponse = Metadata(
    id = "f2fb30e5-4ab6-4a29-b3c1-c7264259ff1c",
    taxYear = "2018-19",
    requestedBy = CalculationRequestor.customer,
    requestedTimestamp = Some("2019-11-15T09:25:15.094Z"),
    calculationReason = CalculationReason.customerRequest,
    calculationTimestamp = Some("2019-11-15T09:35:15.094Z"),
    calculationType = CalculationType.inYear,
    intentToCrystallise = false,
    crystallised = false,
    totalIncomeTaxAndNicsDue = None,
    calculationErrorCount = Some(1)
  )

  val messagesResponse = Messages(Some(Seq(info1,info2)), Some(Seq(warn1,warn2)), Some(Seq(err1,err2)))

  val getCalculationResponse = GetCalculationResponse(metadataResponse, messages = Some(messagesResponse))
  val getCalculationResponseAllData = GetCalculationResponse(metadataResponse, messages = Some(messagesResponse),
    incomeTaxAndNicsCalculated = Some(incomeTax), taxableIncome = Some(taxableIncomeModel), endOfYearEstimate = Some(eoyEstimateResponse),
    allowancesDeductionsAndReliefs = Some(allowancesDeductionsAndReliefs))
  val wrongCalcTypeResponse = GetCalculationResponse(metadataResponse.copy(calculationType = CalculationType.biss), None)


  private val nino          = "AA111111A"
  private val calculationId = "f2fb30e5-4ab6-4a29-b3c1-c7264259ff1c"
  implicit val correlationId = "a1e8057e-fbbc-47a8-a8b4-78d9f015c253"
  private val requestData = GetCalculationRequest(Nino(nino), calculationId)

  trait Test extends MockTaxCalcConnector {
    implicit val hc: HeaderCarrier              = HeaderCarrier()
    implicit val logContext: EndpointLogContext = EndpointLogContext("", "")

    val service = new GetCalculationService(mockTaxCalcConnector)
  }

  "GetCalculationService" when {
    "the service is called" must {
      "return mapped result" in new Test {
        MockTaxCalcConnector
          .getCalculation(requestData)
          .returns(Future.successful(Right(ResponseWrapper(correlationId, getCalculationResponse))))

        await(service.getCalculation(requestData)) shouldBe Right(ResponseWrapper(correlationId,
          getCalculationResponse.copy(getCalculationResponse.metadata.copy(metadataExistence = Some(MetadataExistence(messages = true))))))
      }
      "return mapped result with all data" in new Test {
        MockTaxCalcConnector
          .getCalculation(requestData)
          .returns(Future.successful(Right(ResponseWrapper(correlationId, getCalculationResponseAllData))))

        await(service.getCalculation(requestData)) shouldBe Right(ResponseWrapper(correlationId,
          getCalculationResponseAllData.copy(getCalculationResponseAllData.metadata.copy(metadataExistence = Some(MetadataExistence(messages = true,
            incomeTaxAndNicsCalculated = true, taxableIncome = true, endOfYearEstimate = true, allowancesDeductionsAndReliefs = true))))))
      }

      "not surface unwanted calculation types" in new Test {
        MockTaxCalcConnector
          .getCalculation(requestData)
          .returns(Future.successful(Right(ResponseWrapper(correlationId, wrongCalcTypeResponse))))

        await(service.getCalculation(requestData)) shouldBe Left(ErrorWrapper(correlationId, NotFoundError))
      }
    }

    "the service call is unsuccessful" must {
      "map errors according to spec" when {

        def serviceError(desErrorCode: String, error: MtdError): Unit =
          s"a $desErrorCode error is returned from the service" in new Test {

            MockTaxCalcConnector.getCalculation(requestData)
              .returns(Future.successful(Left(ResponseWrapper(correlationId, DesErrors.single(DesErrorCode(desErrorCode))))))

            await(service.getCalculation(requestData)) shouldBe Left(ErrorWrapper(correlationId, error))
          }

        val input = Seq(
          "INVALID_TAXABLE_ENTITY_ID" -> NinoFormatError,
          "INVALID_CALCULATION_ID"    -> CalculationIdFormatError,
          "NOT_FOUND"                 -> NotFoundError,
          "SERVER_ERROR"              -> DownstreamError,
          "SERVICE_UNAVAILABLE"       -> DownstreamError
        )

        input.foreach(args => (serviceError _).tupled(args))
      }
    }
  }
}
