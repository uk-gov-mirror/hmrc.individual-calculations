/*
 * Copyright 2019 HM Revenue & Customs
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

package v1.models.response.getCalculation.taxableIncome

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, OWrites, Reads, Writes}
import v1.models.response.getCalculation.taxableIncome.detail.CalculationDetail
import v1.models.response.getCalculation.taxableIncome.summary.CalculationSummary

case class TaxableIncome(summary: CalculationSummary, detail: CalculationDetail)

object TaxableIncome {
  implicit val writes: OWrites[TaxableIncome] = Json.writes[TaxableIncome]
  implicit val reads: Reads[TaxableIncome] = (
    (JsPath \ "calculation" \ "taxCalculation" \ "incomeTax").read[CalculationSummary] and
      JsPath.read[CalculationDetail]
  )(TaxableIncome.apply _)

}