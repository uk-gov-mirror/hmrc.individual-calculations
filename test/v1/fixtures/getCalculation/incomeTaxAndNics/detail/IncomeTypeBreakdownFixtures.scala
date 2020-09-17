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

package v1.fixtures.getCalculation.incomeTaxAndNics.detail

import play.api.libs.json.{JsValue, Json}
import v1.models.response.getCalculation.incomeTaxAndNics.detail.{IncomeTypeBreakdown, TaxBand}

object IncomeTypeBreakdownFixtures {

  val json: JsValue = Json.parse("""
      |{
      | "allowancesAllocated" : 100,
      | "incomeTaxAmount" : 200.25,
      | "taxBands" : [
      |   {
      |     "name" : "name",
      |     "rate" : 300.25,
      |     "bandLimit" : 600,
      |     "apportionedBandLimit" : 700,
      |     "income" : 800,
      |     "taxAmount" : 900.25
      |   }
      | ]
      |}
    """.stripMargin)

  val model = IncomeTypeBreakdown(100, 200.25, Some(Seq(TaxBand("name", 300.25, 600, 700, 800, 900.25))))

}
