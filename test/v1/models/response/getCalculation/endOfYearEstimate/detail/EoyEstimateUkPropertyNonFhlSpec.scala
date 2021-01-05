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

package v1.models.response.getCalculation.endOfYearEstimate.detail

import play.api.libs.json.{JsError, JsSuccess, Json}
import support.UnitSpec
import v1.fixtures.getCalculation.endOfYearEstimate.detail.EoyEstimateUkPropertyNonFhlFixtures._

class EoyEstimateUkPropertyNonFhlSpec extends UnitSpec {

  "EoyEstimateUkPropertyNonFHL" when {
    "read from valid Json" should {
      "return a JsSuccess" in {
        eoyEstimateUkPropertyNonFhlDesJson.validate[EoyEstimateUkPropertyNonFhl] shouldBe a[JsSuccess[_]]
      }
      "with the expected EoyEstimateUkDividends object" in {
        eoyEstimateUkPropertyNonFhlDesJson.as[EoyEstimateUkPropertyNonFhl] shouldBe eoyEstimateUkPropertyNonFhlResponse
      }
    }

    "read from invalid Json" should {
      "return a JsError" in {
        eoyEstimateUkPropertyNonFhlInvalidJson.validate[EoyEstimateUkPropertyNonFhl] shouldBe a[JsError]
      }
    }

    "written to Json" should {
      "return the expected JsObject" in {
        Json.toJson(eoyEstimateUkPropertyNonFhlResponse) shouldBe eoyEstimateUkPropertyNonFhlWrittenJson
      }
    }
  }

}
