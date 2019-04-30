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
import java.util.ArrayList;
import java.util.Objects;

public class NGram {
    private List<Event> eventList;

    public NGram(List<Event> eventList) {
        if (eventList == null) {
            throw new IllegalArgumentException("cannot create NGram with null");
        }
        this.eventList = new ArrayList<Event>(eventList);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof NGram)) {
            return false;
        }
        NGram otherNGram = (NGram) other;
        if (otherNGram.eventList.size() != this.eventList.size()) {
            return false;
        }
        for (int i = 0 ; i < otherNGram.eventList.size() ; i++) {
            Event otherEvent = otherNGram.eventList.get(i);
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