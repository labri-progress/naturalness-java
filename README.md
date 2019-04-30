# naturalness

Library implementing ["On the Naturalness of Software"](https://people.inf.ethz.ch/suz/publications/natural.pdf)


```console
mvn compile
```

```console
mvn test
```

```java
List<Sequence> sequenceList = new ArrayList<Sequence>();
Event a = new Event("a");
Event b = new Event("b");
Event c = new Event("c");
Event d = new Event("d");
Event e = new Event("e");
sequenceList.add(new Sequence(a, b, c, d, e));
sequenceList.add(new Sequence(a, b, c, d, c));
NaturalnessModel model = new NaturalnessModel(3,0);
model.learn(sequenceList.get(0));
double ce = model.crossEntropy(sequenceList.get(1));
```