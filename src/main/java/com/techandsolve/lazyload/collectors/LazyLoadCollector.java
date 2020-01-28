package com.techandsolve.lazyload.collectors;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class LazyLoadCollector implements Collector<Integer,LazyLoadCollectorHelper,List<String>>{
    @Override
    public Supplier<LazyLoadCollectorHelper> supplier() {
        return ()->new LazyLoadCollectorHelper();
    }

    @Override
    public BiConsumer<LazyLoadCollectorHelper, Integer> accumulator() {
        return (helper, caso) -> helper.addCaso(caso);
    }

    @Override
    public BinaryOperator<LazyLoadCollectorHelper> combiner() {
        return null;
    }

    @Override
    public Function<LazyLoadCollectorHelper, List<String>> finisher() {
        return (helper) -> helper.getListaCasos();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }
}
