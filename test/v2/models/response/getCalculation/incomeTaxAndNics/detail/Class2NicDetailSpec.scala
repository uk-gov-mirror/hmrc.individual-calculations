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

package v2.models.response.getCalculation.incomeTaxAndNics.detail

import play.api.libs.json.{JsError, JsSuccess, Json}
import support.UnitSpec
import v2.fixtures.getCalculation.incomeTaxAndNics.detail.Class2NicDetailFixtures._

class Class2NicDetailSpec extends UnitSpec {

  "Class2NicDetail" should {

    "read correctly from json" in {
      class2NicDetailDesJson.validate[Class2NicDetail] shouldBe JsSuccess(class2NicDetailModel)
    }

    "read from invalid JSON" should {
      "produce a JsError" in {
        val invalidJson = Json.parse(
          """
            |{
            |   "inputs": {
            |      "personalInformation": {
            |         "class2VoluntaryContributions": false
            |      }
            |   },
            |   "calculation": {
            |      "taxCalculation": {
            |         "nics": {
            |            "class2Nics": {
            |               "weeklyRate": true,
            |               "underSmallProfitThreshold": true,
            |               "actualClass2Nic": false
            |            }
            |         }
            |      }
            |   }
            |}
          """.stripMargin
        )
        invalidJson.validate[Class2NicDetail] shouldBe a[JsError]
      }
    }

    "write correctly to json" in {
      Json.toJson(class2NicDetailModel) shouldBe class2NicDetailMtdJson
    }
  }
}