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

package v1r2.fixtures.getCalculation.incomeTaxAndNics.detail

import play.api.libs.json.{JsValue, Json}
import v1r2.models.response.getCalculation.incomeTaxAndNics.detail.Class2NicDetail

object Class2NicDetailFixtures {
  val class2NicDetailJson: JsValue = Json.parse(
    """
      |{
      |   "weeklyRate" : 100.25,
      |   "weeks" : 200.25,
      |   "limit" : 300.25,
      |   "apportionedLimit" : 400.25,
      |   "underSmallProfitThreshold" : true,
      |   "actualClass2Nic" : false
      |}
    """.stripMargin
  )

  val class2NicDetailModel = Class2NicDetail(
    weeklyRate = Some(100.25),
    weeks = Some(200.25),
    limit = Some(300.25),
    apportionedLimit = Some(400.25),
    underSmallProfitThreshold = true,
    actualClass2Nic = Some(false)
  )
}