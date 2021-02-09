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

package v1r2.models.response.getCalculation.taxableIncome.detail.payPensionsProfit.ukPropertyFhl.detail

import play.api.libs.functional.syntax._
import play.api.libs.json._
import v1r2.models.request.DesTaxYear

case class UkPropertyFhlDefaultCarriedForwardLoss(taxYearLossIncurred: String, currentLossValue: BigInt)

object UkPropertyFhlDefaultCarriedForwardLoss {
  implicit val reads: Reads[UkPropertyFhlDefaultCarriedForwardLoss] = (
    (JsPath \ "taxYearLossIncurred").read[Int].map(DesTaxYear.fromDesIntToString) and
      (JsPath \ "currentLossValue").read[BigInt]
  )(UkPropertyFhlDefaultCarriedForwardLoss.apply _)

  implicit val writes: OWrites[UkPropertyFhlDefaultCarriedForwardLoss] = Json.format[UkPropertyFhlDefaultCarriedForwardLoss]
}
