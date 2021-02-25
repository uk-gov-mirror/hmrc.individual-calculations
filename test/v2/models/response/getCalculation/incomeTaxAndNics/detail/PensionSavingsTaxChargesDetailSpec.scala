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

import play.api.libs.json.{JsError, JsObject, Json}
import support.UnitSpec
import v2.fixtures.getCalculation.incomeTaxAndNics.detail.PensionSavingsTaxChargesDetailFixtures._

class PensionSavingsTaxChargesDetailSpec extends UnitSpec {

  "PensionSavingsTaxChargesDetail" should {

    "read from json correctly" when {
      "provided with valid json" in {
        pensionSavingsTaxChargesDetailDesJson.as[PensionSavingsTaxChargesDetail] shouldBe pensionSavingsTaxChargesDetailModel
      }
    }

    "write to json correctly" when {
      "a valid model is provided" in {
        Json.toJson(pensionSavingsTaxChargesDetailModel) shouldBe pensionSavingsTaxChargesDetailMtdJson
      }
    }

    "read from valid JSON with empty PensionSavingsTaxChargesDetail objects" should {
      "produce an empty PensionSavingsTaxChargesDetail object" in {
        val emptyJson = JsObject.empty

        emptyJson.as[PensionSavingsTaxChargesDetail] shouldBe PensionSavingsTaxChargesDetail.empty
      }
    }

    "read from invalid JSON" should {
      "produce a JsError" in {
        val invalidJson = Json.parse(
          """
            |{
            |	 "excessOfLifeTimeAllowance": {
            |		 "lumpSumBenefitTakenInExcessOfLifetimeAllowance": {
            |			 "amount": true
            |		 }
            |	 }
            |}
          """.stripMargin
        )
        invalidJson.validate[PensionSavingsTaxChargesDetail] shouldBe a[JsError]
      }
    }

    "read from empty JSON" should {
      "produce an empty PensionSavingsTaxChargesDetail object" in {
        val emptyJson = JsObject.empty

        emptyJson.as[PensionSavingsTaxChargesDetail] shouldBe PensionSavingsTaxChargesDetail.empty
      }
    }
  }
}