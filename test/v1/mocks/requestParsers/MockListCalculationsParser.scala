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

package v1.mocks.requestParsers

import org.scalamock.handlers.CallHandler
import org.scalamock.scalatest.MockFactory
import v1.controllers.requestParsers.ListCalculationsParser
import v1.models.errors.ErrorWrapper
import v1.models.requestData.selfAssessment.{ListCalculationsRawData, ListCalculationsRequest}

trait MockListCalculationsParser extends MockFactory {

  val mockListCalculationsParser = mock[ListCalculationsParser]

  object MockListCalculationsParser {
    def parse(data: ListCalculationsRawData): CallHandler[Either[ErrorWrapper, ListCalculationsRequest]] = {
      (mockListCalculationsParser.parseRequest(_: ListCalculationsRawData)).expects(data)
    }
  }

}