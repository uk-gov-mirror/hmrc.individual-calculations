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

package v1.models.response.getCalculation.taxableIncome.detail.payPensionsProfit.eeaPropertyFhl

import play.api.libs.json.{JsError, JsObject, JsValue, Json}
import support.UnitSpec
import v1.fixtures.getCalculation.taxableIncome.{TaxableIncomeJsonFixture, TaxableIncomeModelsFixture}

class EeaPropertyFhlLossClaimsDetailSpec extends UnitSpec {

  "EeaPropertyFhlLossClaimsDetail" when {
    "read from valid JSON" should {
      "produce the expected EeaPropertyFhlLossClaimsDetail object" in {
        TaxableIncomeJsonFixture.desJson.as[EeaPropertyFhlLossClaimsDetail] shouldBe
          TaxableIncomeModelsFixture.eeaPropertyFhlDetailModel
      }
    }

    "read from empty JSON" should {
      "produce an empty EeaPropertyFhlLossClaimsDetail object" in {
        val emptyJson: JsValue = JsObject.empty
        emptyJson.as[EeaPropertyFhlLossClaimsDetail] shouldBe EeaPropertyFhlLossClaimsDetail.empty
      }
    }

    "read from non-empty JSON without incomeSourceType '03'" should {
      "produce an empty EeaPropertyFhlLossClaimsDetail object" in {
        TaxableIncomeJsonFixture.oneSelfEmploymentOnlyDesJson.as[EeaPropertyFhlLossClaimsDetail] shouldBe
          EeaPropertyFhlLossClaimsDetail.empty
      }
    }

    "read from invalid JSON" should {
      "produce a JsError" in {
        val invalidDesJson: JsValue = Json.parse(
          """
            |{
            |   "calculation": {
            |      "lossesAndClaims": {
            |         "resultOfClaimsApplied": "no"
            |      }
            |   }
            |}
          """.stripMargin
        )

        invalidDesJson.validate[EeaPropertyFhlLossClaimsDetail] shouldBe a[JsError]
      }
    }

    "written to JSON" should {
      "produce the expected JsObject" in {
        val mtdJson: JsValue = (TaxableIncomeJsonFixture.mtdJson \ "detail" \ "payPensionsProfit" \
          "businessProfitAndLoss" \ "eeaPropertyFhl" \ "lossClaimsDetail").get
        Json.toJson(TaxableIncomeModelsFixture.eeaPropertyFhlDetailModel) shouldBe mtdJson
      }
    }
  }
}
