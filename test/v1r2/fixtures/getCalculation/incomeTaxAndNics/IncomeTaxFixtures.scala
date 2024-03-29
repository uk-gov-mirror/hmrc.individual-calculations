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

package v1r2.fixtures.getCalculation.incomeTaxAndNics

import play.api.libs.json.{JsValue, Json}
import v1r2.models.response.getCalculation.incomeTaxAndNics.IncomeTax
import v1r2.models.response.getCalculation.incomeTaxAndNics.detail._
import v1r2.models.response.getCalculation.incomeTaxAndNics.summary._

object IncomeTaxFixtures {

  val incomeTaxJson: JsValue = Json.parse(
    """
      |{
      |   "calculation":{
      |      "taxCalculation":{
      |         "incomeTax":{
      |            "incomeTaxCharged": 100.25,
      |            "payPensionsProfit":{
      |               "allowancesAllocated": 300,
      |               "incomeTaxAmount": 400.25
      |            }
      |         },
      |         "totalIncomeTaxAndNicsDue": 200.25
      |      }
      |   },
      |   "inputs":{
      |      "personalInformation":{
      |         "taxRegime": "UK"
      |      }
      |   }
      |}
    """.stripMargin)

  val incomeTaxOutputJson: JsValue = Json.parse(
    """
      |{
      |   "summary":{
      |      "incomeTax":{
      |         "incomeTaxCharged": 100.25
      |      },
      |      "totalIncomeTaxAndNicsDue": 200.25,
      |      "taxRegime": "UK"
      |   },
      |   "detail":{
      |      "incomeTax":{
      |         "payPensionsProfit":{
      |            "allowancesAllocated": 300,
      |            "incomeTaxAmount": 400.25
      |         }
      |      }
      |   }
      |}
    """.stripMargin)

  val incomeTaxCalcSummary =
    CalculationSummary(
      IncomeTaxSummary(
        100.25, None, None, None, None, None, None, None
      ),
      None, None, None, None, None, None, 200.25, "UK"
    )

  val incomeTaxCalcDetail =
    CalculationDetail(
      IncomeTaxDetail(
        Some(
          IncomeTypeBreakdown(
            300, 400.25, None
          )
        ),
        None, None, None, None, None
      ),
      None, None, None, None
    )

  val incomeTaxModel = IncomeTax(incomeTaxCalcSummary, incomeTaxCalcDetail)
}