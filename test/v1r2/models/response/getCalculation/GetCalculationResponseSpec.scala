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

package v1r2.models.response.getCalculation

import play.api.libs.json._
import support.UnitSpec
import v1r2.fixtures.getCalculation.GetCalculationResponseFixtures._

class GetCalculationResponseSpec extends UnitSpec {

  "GetCalculationResponse" should {

    "successfully read from json" when {
      "provided with valid json with only metadata" in {
        desJsonWithoutOptionalParts.as[GetCalculationResponse] shouldBe calculationResponse
      }

      "provided with valid json with all optional top-level parts" in {
        desJsonWithAllParts.as[GetCalculationResponse] shouldBe calculationResponseAllParts
      }
    }

    "fail to read from json" when {
      "invalid metadata JSON" in {
        // Mandatory value at /metadata/requestedBy is missing...
        val invalidDesJson: JsValue = Json.parse(
          """{
            |    "metadata":{
            |       "calculationId": "f2fb30e5-4ab6-4a29-b3c1-c7264259ff1c",
            |       "taxYear": 2019,
            |       "requestedBy": "me",
            |       "requestedTimestamp": "2019-11-15T09:25:15.094Z",
            |       "calculationReason": "customerRequest",
            |       "calculationTimestamp": "2019-11-15T09:35:15.094Z",
            |       "calculationType": "inYear",
            |       "periodFrom": "1-2018",
            |       "periodTo": "1-2019"
            |     },
            |     "messages" :{
            |        "errors":[
            |        {"id":"id1", "text":"text1"}
            |        ]
            |     }
            |}""".stripMargin)

        invalidDesJson.validate[GetCalculationResponse] shouldBe a[JsError]
      }

      "read invalid income tax and NICs JSON" in {
        // Mandatory value at /calculation/taxCalculation/totalIncomeTaxAndNicsDue is missing...
        val invalidDesJson: JsValue = desJsonWithoutOptionalParts.as[JsObject] ++
          Json.parse(
            """
              |{
              | "calculation" : {
              |   "taxCalculation" : {
              |     "incomeTax" : {
              |       "incomeTaxCharged" : 100.25,
              |       "totalIncomeReceivedFromAllSources": 123,
              |       "totalTaxableIncome": 234,
              |       "payPensionsProfit" : {
              |           "allowancesAllocated" : 300.25,
              |           "incomeTaxAmount": 400.25
              |        }
              |     }
              |   }
              |  },
              | "inputs" : {
              |  "personalInformation" : {
              |    "taxRegime" : "UK"
              |  }
              | }
              |}
                      """.stripMargin).as[JsObject]

        invalidDesJson.validate[GetCalculationResponse] shouldBe a[JsError]
      }

      "read invalid taxable income JSON" in {
        // Mandatory value at /calculation/taxCalculation/incomeTax/totalTaxableIncome is missing...
        val invalidDesJson: JsValue = desJsonWithoutOptionalParts.as[JsObject] ++
          Json.parse(
            """
              |{
              | "calculation" : {
              |   "taxCalculation" : {
              |     "incomeTax" : {
              |       "incomeTaxCharged" : 100.25,
              |       "totalIncomeReceivedFromAllSources": 123,
              |       "payPensionsProfit" : {
              |           "allowancesAllocated" : 300.25,
              |           "incomeTaxAmount": 400.25
              |        }
              |     },
              |     "totalIncomeTaxAndNicsDue" : 200.25
              |   }
              |  },
              | "inputs" : {
              |  "personalInformation" : {
              |    "taxRegime" : "UK"
              |  }
              | }
              |}
                      """.stripMargin).as[JsObject]

        invalidDesJson.validate[GetCalculationResponse] shouldBe a[JsError]
      }
    }

    "write correctly to json" when {
      "using a model with only metadata and messages" in {
        Json.toJson(calculationResponse) shouldBe writtenJsonWithoutOptionalParts
      }
      "using a model with all parts" in {
        Json.toJson(calculationResponseAllParts) shouldBe writtenJson
      }
    }
  }
}