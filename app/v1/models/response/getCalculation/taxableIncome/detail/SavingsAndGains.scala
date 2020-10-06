/*
 * Copyright 2020 HM Revenue & Customs
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

package v1.models.response.getCalculation.taxableIncome.detail

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import utils.NestedJsonReads

case class SavingsAndGains(incomeReceived: BigInt, taxableIncome: BigInt, ukSavings: Option[Seq[UkSaving]])

object SavingsAndGains extends NestedJsonReads {
  implicit val reads: Reads[SavingsAndGains] = {
    val commonJsPath: JsPath = JsPath \ "calculation" \ "taxCalculation" \ "incomeTax" \ "savingsAndGains"
    (
      (commonJsPath \ "incomeReceived").read[BigInt] and
        (commonJsPath \ "taxableIncome").read[BigInt] and
        (JsPath \ "calculation" \ "savingsAndGainsIncome").readIncomeSourceTypeSeq[UkSaving](incomeSourceType = "09")
    )(SavingsAndGains.apply _)
  }

  implicit val writes: OWrites[SavingsAndGains] = Json.writes[SavingsAndGains]
}
