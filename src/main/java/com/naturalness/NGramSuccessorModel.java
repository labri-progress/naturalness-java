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

import java.util.HashMap;
import java.util.Map;

public class NGramSuccessorModel<T extends Comparable> {
    private final int NULL_PROBABILITY = 0;
    private Map<Event<T>, Integer> successorMap;
    private int occurence;
    

    public NGramSuccessorModel() {
        this.successorMap = new HashMap<>();
        this.occurence = 0;
    }

    public double getProbability(Event<T> event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }
        if (!successorMap.containsKey(event)) {
            return NULL_PROBABILITY;
        }
        double eventOccurence = successorMap.get(event).doubleValue();
        double proba = eventOccurence / occurence;
        return  proba;
    }

    public void learn(Event<T> event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }
        if (!successorMap.containsKey(event)) {
            successorMap.put(event, Integer.valueOf(1));
        } else {
            int newOccurence = successorMap.get(event).intValue()+1;
            successorMap.replace(event, Integer.valueOf(newOccurence));
        }
        occurence++;
    }

}