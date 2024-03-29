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

package v1r2.fixtures.getCalculation.endOfYearEstimate.detail

import play.api.libs.json.{ JsObject, JsValue, Json }
import v1r2.models.response.getCalculation.endOfYearEstimate.detail.EoyEstimateSelfEmployment

object EoyEstimateSelfEmploymentFixtures {

  val selfEmploymentId: String   = "AB123456789"
  val taxableIncome: BigInt      = 1011
  val finalised: Option[Boolean] = Some(false)

  val eoyEstimateSelfEmploymentResponse: EoyEstimateSelfEmployment =
    EoyEstimateSelfEmployment(selfEmploymentId, taxableIncome, finalised)

  val eoyEstimateSelfEmploymentDesJson: JsValue = Json.parse(s"""{
      |  "incomeSourceId": "$selfEmploymentId",
      |  "taxableIncome": $taxableIncome,
      |  "finalised": ${finalised.get},
      |  "incomeSourceType" : "01"
      |}""".stripMargin)

  val eoyEstimateSelfEmploymentDesJsonWrongIncomeSourceType: JsValue = Json.parse(s"""{
      |  "incomeSourceId": "$selfEmploymentId",
      |  "taxableIncome": $taxableIncome,
      |  "finalised": ${finalised.get},
      |  "incomeSourceType" : "wrong"
      |}""".stripMargin)

  val eoyEstimateSelfEmploymentDesJsonMissingFields: JsValue = Json.parse(s"""{
      |  "incomeSourceId": "$selfEmploymentId",
      |  "taxableIncome": $taxableIncome,
      |  "incomeSourceType" : "01"
      |}""".stripMargin)

  val eoyEstimateSelfEmploymentWrittenJson: JsValue = Json.parse(s"""{
      |  "selfEmploymentId": "$selfEmploymentId",
      |  "taxableIncome": $taxableIncome,
      |  "finalised": ${finalised.get}
      |}""".stripMargin)

  val eoyEstimateSelfEmploymentWrittenJsonMissingFields: JsValue = Json.parse(s"""{
      |  "selfEmploymentId": "$selfEmploymentId",
      |  "taxableIncome": $taxableIncome
      |}""".stripMargin)

  val eoyEstimateSelfEmploymentWrittenJsonObject: JsObject = Json.obj("selfEmployments" -> Seq(eoyEstimateSelfEmploymentWrittenJson))

  val eoyEstimateSelfEmploymentInvalidJson: JsValue = Json.parse(s"""{
      |  "taxableIncome": $taxableIncome,
      |  "incomeSourceType" : "01"
      |}""".stripMargin)

  def eoyEstimateSelfEmploymentResponseFactory(selfEmploymentId: String = selfEmploymentId,
                                               taxableIncome: BigInt = taxableIncome,
                                               finalised: Option[Boolean] = finalised): EoyEstimateSelfEmployment =
    EoyEstimateSelfEmployment(selfEmploymentId, taxableIncome, finalised)

}
