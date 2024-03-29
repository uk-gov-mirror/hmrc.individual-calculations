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

package v1r2.models.response.getCalculation.incomeTaxAndNics.detail

import play.api.libs.functional.syntax._
import play.api.libs.json._
import utils.NestedJsonReads

case class CalculationDetail(incomeTax: IncomeTaxDetail,
                             studentLoans: Option[Seq[StudentLoans]],
                             pensionSavingsTaxCharges: Option[PensionSavingsTaxCharges],
                             nics: Option[NicDetail],
                             taxDeductedAtSource: Option[TaxDeductedAtSource])

object CalculationDetail extends NestedJsonReads {
  implicit val writes: OWrites[CalculationDetail] = Json.writes[CalculationDetail]

  implicit val reads: Reads[CalculationDetail] = (
    (JsPath \ "calculation").read[IncomeTaxDetail] and
      (JsPath \ "calculation" \ "studentLoans").readNullable[Seq[StudentLoans]] and
      (JsPath \ "calculation" \ "pensionSavingsTaxCharges").readNullable[PensionSavingsTaxCharges] and
      (JsPath \ "calculation" \ "taxCalculation" \ "nics").readNestedNullable[NicDetail].map {
        case Some(NicDetail.empty) => None
        case other => other
      } and
      (JsPath \ "calculation" \ "taxDeductedAtSource").readNestedNullable[TaxDeductedAtSource].map {
        case Some(TaxDeductedAtSource.empty) => None
        case other => other
      }
    ) (CalculationDetail.apply _)
}