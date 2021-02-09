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

package v1r2.models.response.getCalculation.taxableIncome.detail

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import utils.NestedJsonReads
import v1.models.response.getCalculation.taxableIncome.detail.{UkSaving, UkSecurity}

case class SavingsAndGainsV1R2(incomeReceived: BigInt,
                               taxableIncome: BigInt,
                               ukSavings: Option[Seq[UkSaving]],
                               ukSecurities: Option[Seq[UkSecurity]])


object SavingsAndGainsV1R2 extends NestedJsonReads {
  implicit val reads: Reads[SavingsAndGainsV1R2] = {
    val savingsAndGainsJsPath: JsPath = JsPath \ "calculation" \ "taxCalculation" \ "incomeTax" \ "savingsAndGains"
    val savingsAndGainsIncomeJsPath: JsPath = JsPath \ "calculation" \ "savingsAndGainsIncome" \ "ukSavingsAndGainsIncome"
    (
      (savingsAndGainsJsPath \ "incomeReceived").read[BigInt] and
        (savingsAndGainsJsPath \ "taxableIncome").read[BigInt] and
        savingsAndGainsIncomeJsPath.readIncomeSourceTypeSeq[UkSaving](incomeSourceType = "09") and
        savingsAndGainsIncomeJsPath.readIncomeSourceTypeSeq[UkSecurity](incomeSourceType = "18")
      ) (SavingsAndGainsV1R2.apply _)
  }

  implicit val writes: OWrites[SavingsAndGainsV1R2] = Json.writes[SavingsAndGainsV1R2]
}
