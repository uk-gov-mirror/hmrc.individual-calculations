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

package v1r2.models.response.getCalculation.incomeTaxAndNics.detail

import play.api.libs.json.{JsError, Json}
import support.UnitSpec
import v1r2.fixtures.getCalculation.incomeTaxAndNics.detail.NicBandFixtures._

class NicBandSpec extends UnitSpec {

  "NicBand" should {

    "read from json correctly" when {

      "provided with valid json" in {
        nicBandJson.as[NicBand] shouldBe nicBandModel
      }
    }

    "write to json correctly" when {

      "a valid model is provided" in {
        Json.toJson(nicBandModel) shouldBe nicBandJson
      }
    }

    "read from invalid JSON" should {
      "produce a JsError" in {
        val invalidJson = Json.parse(
          """
            |{
            |	"name": true,
            |	"rate": 100.25,
            |	"threshold": 200,
            |	"apportionedThreshold": 300,
            |	"income": 400,
            |	"amount": 500.25
            |}
          """.stripMargin
        )
        invalidJson.validate[NicBand] shouldBe a[JsError]
      }
    }
  }
}
