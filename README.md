# naturalness

Library implementing ["On the Naturalness of Software"](https://people.inf.ethz.ch/suz/publications/natural.pdf)


```console
mvn compile
```

```console
mvn test
```

```java
NaturalnessModel<String> model = new NaturalnessModel<>(3, 0);
Event<String> a = new Event<>("a");
Event<String> b = new Event<>("b");
Event<String> c = new Event<>("c");
Event<String> d = new Event<>("d");
model.learn(a, b, c);
double probaA = model.getProbability(new NGram(), a);
assertEquals(probaA, 1.0, DELTA);
double probaB = model.getProbability(new NGram(a), b);
assertEquals(probaB, 1.0, DELTA);
double probaC = model.getProbability(new NGram(a,b), c);
assertEquals(probaC, 1.0, DELTA);
model.learn(a, b, d);
probaA = model.getProbability(new NGram(), a);
assertEquals(probaA, 1.0, DELTA);
probaB = model.getProbability(new NGram(a), b);
assertEquals(probaB, 1.0, DELTA);
probaC = model.getProbability(new NGram(a, b), c);
assertEquals(0.5, probaC, DELTA);
double probaD = model.getProbability(new NGram(a, b), d);
assertEquals(probaD,0.5, DELTA);
double proba = model.getProbability(new NGram(a, b), d);
System.out.println(proba);
double ce = model.crossEntropy(new Sequence(a, b, d));
assertTrue(ce > DELTA);
```