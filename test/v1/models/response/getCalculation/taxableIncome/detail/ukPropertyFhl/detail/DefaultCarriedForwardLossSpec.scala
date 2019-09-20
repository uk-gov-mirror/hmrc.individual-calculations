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

package v1.models.response.getCalculation.taxableIncome.detail.ukPropertyFhl.detail

import play.api.libs.json.{JsSuccess, Json}
import support.UnitSpec
import v1.fixtures.taxableIncome.ukProperty.DefaultCarriedForwardLossFixtures._
import v1.models.utils.JsonErrorValidators

class DefaultCarriedForwardLossSpec extends UnitSpec with JsonErrorValidators {

  "CarriedForwardLoss" when {
    "read from valid Json" should {

      testMandatoryProperty[DefaultCarriedForwardLoss](carriedForwardLossDesJson)("/taxYearLossIncurred")
      testMandatoryProperty[DefaultCarriedForwardLoss](carriedForwardLossDesJson)("/currentLossValue")

      "return a JsSuccess" in {
        carriedForwardLossDesJson.validate[DefaultCarriedForwardLoss] shouldBe a[JsSuccess[_]]
      }

      "with the expected CarriedForwardLoss object" in {
        carriedForwardLossDesJson.as[DefaultCarriedForwardLoss] shouldBe carriedForwardLossResponse
      }
    }

    "writes to Json" should {
      "return the expected JsObject" in {
        Json.toJson(carriedForwardLossResponse) shouldBe carriedForwardLossWrittenJson
      }
    }
  }

}
