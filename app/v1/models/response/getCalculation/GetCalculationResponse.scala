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

package v1.models.response.getCalculation

import play.api.libs.functional.syntax._
import play.api.libs.json.{ JsPath, Json, OWrites, Reads }
import v1.models.response.common.{ IncomeTax, Messages, Metadata }

case class GetCalculationResponse(
                                   metadata: Metadata,
                                   incomeTax: Option[IncomeTax] = None,
                                   messages: Option[Messages] = None
                                 )

object GetCalculationResponse {
  implicit val writes: OWrites[GetCalculationResponse] = Json.writes[GetCalculationResponse]

  implicit val reads: Reads[GetCalculationResponse] = (
    JsPath.read[Metadata] and
      JsPath.readNullable[IncomeTax].orElse(Reads.pure(None)) and
      JsPath.readNullable[Messages].map {
        case Some(messages) if messages.hasMessages => Some(messages)
        case _                                      => None
      }
  )(GetCalculationResponse.apply _)
}