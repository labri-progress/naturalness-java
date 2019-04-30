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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class NaturalnessModel<T extends Comparable> {
    private final int DEPTH_DEFAULT = 3;
    private final double PROBA_OF_UNKNOWN_DEFAULT = 1e-6;

    private int depth;
    private double probaOfUnknown;
    private Map<NGram<T>, NGramSuccessorModel> ngramMap;

    public NaturalnessModel() {
        depth = DEPTH_DEFAULT;
        probaOfUnknown = PROBA_OF_UNKNOWN_DEFAULT;
        ngramMap = new HashMap<>();
    }

    public NaturalnessModel(int depth, double probaOfUnknown) {
        this();
        this.depth = depth;
        this.probaOfUnknown = probaOfUnknown;
    }

    public int getDepth() {
        return depth;
    }

    public double getProbaOfUnknown() {
        return probaOfUnknown;
    }

    public double crossEntropy(Sequence sequence) {
        if (sequence == null) {
            throw new IllegalArgumentException("sequence cannot be null");
        }
        List<Event> eventList = sequence.getEventList();
        
        if (eventList.size() == 0 ) return probaOfUnknown;

        double probabilitySum = 0;

        for (int i = 0; i < eventList.size(); i++) {
            Event currentEvent = eventList.get(i);
            NGram currentNgram = sequence.getNgram(i, depth);
            double modelProba = getProbability(currentNgram, currentEvent);
            double proba;
            if (modelProba == 0) {
                proba = probaOfUnknown;
            } else {
                proba = modelProba * (1 - probaOfUnknown);
            }
            probabilitySum = probabilitySum + Math.log(proba)/Math.log(2);
        }
        return - (probabilitySum / eventList.size());
    }

    public double getProbability(NGram ngram, Event event) {
        if (ngram == null || event == null) {
            return 0;
        }
        if (! ngramMap.containsKey(ngram)) {
            return 0;
        }
        return ngramMap.get(ngram).getProbability(event);
    }

    public final void learn(T... events) {
        learn(Arrays.asList(events));
    }

    public final void learn(List<T> eventList) {
        learn(new Sequence(eventList));
    }

    public void learn(Sequence sequence) {
        List<Event> eventList = sequence.getEventList();
        for (int i = 0; i < eventList.size() ; i++) {
            NGram ngram = sequence.getNgram(i, depth);
            if (!ngramMap.containsKey(ngram)) {
                ngramMap.put(ngram, new NGramSuccessorModel());
            }
            NGramSuccessorModel ngramSuccessor = ngramMap.get(ngram);
            ngramSuccessor.learn(eventList.get(i));   
        }
    }
}