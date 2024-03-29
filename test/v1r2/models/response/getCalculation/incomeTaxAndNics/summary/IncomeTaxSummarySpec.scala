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

package v1r2.models.response.getCalculation.incomeTaxAndNics.summary

import play.api.libs.json.{JsError, Json}
import support.UnitSpec
import v1r2.fixtures.getCalculation.incomeTaxAndNics.summary.IncomeTaxSummaryFixtures._

class IncomeTaxSummarySpec extends UnitSpec {

  "IncomeTaxSummary" should {

    "read from json correctly" when {

      "provided with valid json" in {
        incomeTaxSummaryJson.as[IncomeTaxSummary] shouldBe incomeTaxSummaryModel
      }
    }

    "write to json correctly" when {

      "a valid model is provided" in {
        Json.toJson(incomeTaxSummaryModel) shouldBe incomeTaxSummaryJson
      }
    }

    "read from invalid JSON" should {
      "produce a JsError" in {
        val invalidJson = Json.parse(
          """
            |{
            |   "incomeTaxCharged": true,
            |   "incomeTaxDueAfterReliefs": 1525.22,
            |   "incomeTaxDueAfterGiftAid": 120.10,
            |   "totalNotionalTax": 1900.58,
            |   "totalPensionSavingsTaxCharges": 2000.58,
            |   "statePensionLumpSumCharges": 300.99,
            |   "incomeTaxDueAfterTaxReductions": 1300.58,
            |   "totalIncomeTaxDue": 1000.58
            |}
          """.stripMargin
        )
        invalidJson.validate[IncomeTaxSummary] shouldBe a[JsError]
      }
    }
  }
}