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

public class SequenceSuite<T extends Comparable> {
    private final int DEPTH_DEFAULT = 3;
    private final double PROBA_OF_UNKNOWN_DEFAULT = 1e-6;

    private int depth;
    private double probaOfUnknown;
    private List<Sequence<T>> sequenceList;

    public SequenceSuite(List<Sequence<T>> sequenceList) {
        if (sequenceList == null) {
            throw new IllegalArgumentException("SequenceSuite new with null");
        }
        this.depth = DEPTH_DEFAULT;
        this.probaOfUnknown = PROBA_OF_UNKNOWN_DEFAULT;
        this.sequenceList = new ArrayList<>(sequenceList);
    }

    public SequenceSuite(List<Sequence<T>> sequenceList, int depth, double probaOfUnknown) {
        this(sequenceList);
        this.depth = depth;
        this.probaOfUnknown = probaOfUnknown;
    }

    public Ranking getMoreNatural() {
        if (sequenceList.size() == 0) {
            throw new IllegalStateException("SequenceSuite is empty");
        }

        double minEntropy = -Math.log(probaOfUnknown)/Math.log(2);
        Sequence<T> minSequence = sequenceList.get(0);

        if (sequenceList.size() == 1) {
            return new Ranking(sequenceList.get(0), minEntropy);
        }

        for (int i = 0; i < sequenceList.size(); i++) {
            NaturalnessModel<T> model = new NaturalnessModel<>(this.depth, this.probaOfUnknown);
            List<Sequence<T>> suiteCopy = new ArrayList<>(sequenceList);
            Sequence current = suiteCopy.remove(i);
            for (Sequence<T> sequence : suiteCopy) {
                model.learn(sequence);
            }
            double currentCrossEntropy = model.crossEntropy(current);
            if (currentCrossEntropy < minEntropy) {
                minEntropy = currentCrossEntropy;
                minSequence = current;
            }
        }
        return new Ranking(minSequence, minEntropy);
    }

    public List<Ranking> rank() {
        if (sequenceList.size() == 0) {
            return new ArrayList<Ranking>();
        }
        List<Ranking> rankingList = new ArrayList<Ranking>();
        Ranking moreNatural = getMoreNatural();
        rankingList.add(moreNatural);
        List<Sequence> suiteCopy = new ArrayList<Sequence>(sequenceList);
        suiteCopy.remove(moreNatural.getSequence());
        SequenceSuite other = new SequenceSuite(suiteCopy);
        rankingList.addAll(other.rank());
        return rankingList;
    }
}