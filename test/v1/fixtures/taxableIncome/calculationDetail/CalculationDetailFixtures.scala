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

package v1.fixtures.taxableIncome.calculationDetail

import play.api.libs.json.{ JsObject, JsValue, Json }
import v1.fixtures.taxableIncome.calculationDetail.DividendsFixtures._
import v1.fixtures.taxableIncome.calculationDetail.PayPensionsProfitFixtures._
import v1.fixtures.taxableIncome.calculationDetail.SavingsAndGainsFixtures._
import v1.models.response.getCalculation.taxableIncome.detail.CalculationDetail

object CalculationDetailFixtures {
  val detailResponse = CalculationDetail(Some(payPensionsProfitResponse), Some(savingsAndGainsResponse), Some(dividendsResponse))

  val fullDividendsDesJson: JsObject =
    Json.obj("calculation" -> Json.obj("taxCalculation" -> Json.obj("incomeTax" -> Json.obj("dividends" -> dividendsDesJson))))

  val detailDesJson: JsValue =
    payPensionsProfitDesJson.as[JsObject].deepMerge(Json.obj("calculation" -> savingsAndGainsDesJson).deepMerge(fullDividendsDesJson))

  val dividendsJsonComponent: JsObject         = Json.obj("dividends" -> dividendsWrittenJson)
  val savingsAndGainsJsonComponent: JsObject   = Json.obj("savingsAndGains" -> savingsAndGainsWrittenJson)
  val payPensionsProfitJsonComponent: JsObject = Json.obj("payPensionsProfit" -> payPensionsProfitWrittenJson)
  val detailWrittenJson: JsValue               = dividendsJsonComponent.deepMerge(savingsAndGainsJsonComponent).deepMerge(payPensionsProfitJsonComponent)
  val emptyDetailResponse                      = CalculationDetail(None, None, None)

}
