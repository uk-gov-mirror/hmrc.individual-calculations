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

import play.api.libs.json._
import support.UnitSpec
import v1.fixtures.getCalculation.taxableIncome.detail.CalculationDetailFixtures._

class CalculationDetailSpec extends UnitSpec {

  val emptyJson: JsObject = JsObject.empty

  "Detail" when {
    "read from valid Json" should {
      "return a JsSuccess" in {
        detailDesJson.validate[CalculationDetail] shouldBe a[JsSuccess[_]]
      }
      "with the expected Detail object" in {
        detailDesJson.as[CalculationDetail] shouldBe detailResponse
      }
    }

    "read from valid Json without PayPensionsProfit" should {
      "return a JsSuccess" in {
        detailDesJsonWithoutPPP.validate[CalculationDetail] shouldBe a[JsSuccess[_]]
      }
      "with the expected Detail object" in {
        detailDesJsonWithoutPPP.as[CalculationDetail] shouldBe detailResponseWithoutPPP
      }
    }

    "read from valid Json without SavingsAndGains" should {
      "return a JsSuccess" in {
        detailDesJsonWithoutSAG.validate[CalculationDetail] shouldBe a[JsSuccess[_]]
      }
      "with the expected Detail object" in {
        detailDesJsonWithoutSAG.as[CalculationDetail] shouldBe detailResponseWithoutSAG
      }
    }

    "read from valid Json without Dividends" should {
      "return a JsSuccess" in {
        detailDesJsonWithoutDiv.validate[CalculationDetail] shouldBe a[JsSuccess[_]]
      }
      "with the expected Detail object" in {
        detailDesJsonWithoutDiv.as[CalculationDetail] shouldBe detailResponseWithoutDiv
      }
    }

    "read from empty Json" should {
      "return a JsSuccess" in {
        emptyJson.validate[CalculationDetail] shouldBe a[JsSuccess[_]]
      }
      "with the expected Detail object" in {
        emptyJson.as[CalculationDetail] shouldBe CalculationDetail.empty
      }
    }

    "written to Json" should {
      "return the expected JsObject" in {
        Json.toJson(detailResponse) shouldBe detailWrittenJson
      }
    }

    "written to Json without optional fields" should {
      "return the expected JsObject" in {
        Json.toJson(detailResponseWithoutPPP) shouldBe detailWrittenJsonWithoutPPP
        Json.toJson(detailResponseWithoutSAG) shouldBe detailWrittenJsonWithoutSAG
        Json.toJson(detailResponseWithoutDiv) shouldBe detailWrittenJsonWithoutDiv
      }
    }
  }

}
