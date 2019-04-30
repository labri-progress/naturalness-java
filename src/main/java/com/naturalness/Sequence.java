/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package com.naturalness;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class Sequence {
  private List<Event> eventList;

  public Sequence() {
    eventList = new ArrayList<Event>();
  }

  public Sequence(List<Event> eventList) {
    this.eventList = new ArrayList<Event>(eventList);
  }

  public List<Event> getEventList() {
    return new ArrayList<Event>(eventList);
  }

  public void append(Event event) {
    eventList.add(event);
  }

  public NGram getNgram(int before, int size) {
    List<Event> ngramEventList = new ArrayList<Event>();
    if (before > 0) {
      int from = Math.max(0, before - size);
      for (int i = from; i < before ; i++) {
        ngramEventList.add(eventList.get(i));
      }
    }
    return new NGram(ngramEventList);
  }

  @Override
    public boolean equals(Object other) {
        if (!(other instanceof Sequence)) {
            return false;
        }
        Sequence otherSequence = (Sequence) other;
        if (otherSequence.eventList.size() != this.eventList.size()) {
            return false;
        }
        for (int i = 0 ; i < otherSequence.eventList.size() ; i++) {
            Event otherEvent = otherSequence.eventList.get(i);
            Event thisEvent = this.eventList.get(i);
            if (! otherEvent.equals(thisEvent)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        String value = this.eventList.stream().map( e -> e.getValue()).reduce("" , (accu, cur) -> accu + cur);
        return Objects.hash(value);
    }
}