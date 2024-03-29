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

package v1r2.controllers

import cats.data.EitherT
import cats.implicits._
import javax.inject.{Inject, Singleton}
import play.api.http.MimeTypes
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import utils.{IdGenerator, Logging}
import v1r2.controllers.requestParsers.ListCalculationsParser
import v1r2.models.errors._
import v1r2.models.outcomes.ResponseWrapper
import v1r2.models.request.listCalculations.ListCalculationsRawData
import v1r2.models.response.listCalculations.ListCalculationsResponse
import v1r2.services.{EnrolmentsAuthService, ListCalculationsService, MtdIdLookupService}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ListCalculationsController @Inject()(
                                            val authService: EnrolmentsAuthService,
                                            val lookupService: MtdIdLookupService,
                                            listCalculationsParser: ListCalculationsParser,
                                            listCalculationsService: ListCalculationsService,
                                            cc: ControllerComponents,
                                            idGenerator: IdGenerator)(implicit ec: ExecutionContext)
  extends AuthorisedController(cc)
    with BaseController
    with Logging {

  implicit val endpointLogContext: EndpointLogContext =
    EndpointLogContext(
      controllerName = "ListCalculationsController",
      endpointName = "listCalculations"
    )

  def listCalculations(nino: String, taxYear: String): Action[AnyContent] =
    authorisedAction(nino).async { implicit request =>

      implicit val correlationId: String = request.headers.get("CorrelationId") match {
        case None => idGenerator.getCorrelationId
        case Some(id) => id
      }
      logger.info(message = s"[${endpointLogContext.controllerName}][${endpointLogContext.endpointName}] " +
        s"with correlationId : $correlationId")
      val rawData = ListCalculationsRawData(nino, taxYear)
      val result =
        for {
          parsedRequest <- EitherT.fromEither[Future](listCalculationsParser.parseRequest(rawData))
          desResponse   <- EitherT(listCalculationsService.listCalculations(parsedRequest))
          response      <- EitherT.fromEither[Future](notFoundErrorWhenEmpty(desResponse))
        } yield {
          logger.info(
            s"[${endpointLogContext.controllerName}][${endpointLogContext.endpointName}] - " +
              s"Success response received with CorrelationId: ${response.correlationId}"
          )

          Ok(Json.toJson(response.responseData))
            .withApiHeaders(response.correlationId)
            .as(MimeTypes.JSON)
        }

      result.leftMap { errorWrapper =>
        val resCorrelationId = errorWrapper.correlationId
        val result = errorResult(errorWrapper).withApiHeaders(resCorrelationId)
        logger.warn(
          s"[${endpointLogContext.controllerName}][${endpointLogContext.endpointName}] - " +
            s"Error response received with CorrelationId: $resCorrelationId")
        result
      }.merge
    }

  private def notFoundErrorWhenEmpty(responseWrapper: ResponseWrapper[ListCalculationsResponse]) =
    responseWrapper.toErrorWhen {
      case response if response.calculations.isEmpty => NotFoundError
    }

  private def errorResult(errorWrapper: ErrorWrapper) = {
    (errorWrapper.error: @unchecked) match {
      case BadRequestError | NinoFormatError | TaxYearFormatError | RuleTaxYearNotSupportedError | RuleTaxYearRangeExceededError =>
        BadRequest(Json.toJson(errorWrapper))
      case NotFoundError   => NotFound(Json.toJson(errorWrapper))
      case DownstreamError => InternalServerError(Json.toJson(errorWrapper))
    }
  }
}
