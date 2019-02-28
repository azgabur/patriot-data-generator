/*
 * Copyright 2019 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework.generator.device;

import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.device.timeSimulation.TimeSimulation;
import io.patriot_framework.generator.network.NetworkAdapter;
import io.patriot_framework.generator.converter.DataConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract class for device Composition - one unit with multiple DataFeeds
 *
 * @param <E> type of generated data
 * @param <T> type of object with which all DataFeeds operate
 */
public abstract class AbstractComposition<E,T> extends AbstractDevice implements Composition<T> {

    private TimeSimulation ts;

    private DataConverter<E,T> transform;

    private List<DataFeed<T>> dataFeed;

//    private Class<E> outputType;
//    private Class<T> inputType;

    public AbstractComposition(String label) {
        super(label);

//        if (!inputType.isAssignableFrom(outputType)) {
//            throw new IllegalArgumentException("DataFeed type is not castable to Sensors type");
//        }
    }

    @Override
    public List<E> requestData(Object... param) {
        List<E> result = new ArrayList<>();
        HashMap<String, E> networkData = new HashMap<>();

        for(DataFeed<T> df : dataFeed) {
            E newValue = transform.transform(df.getNextValue());
            networkData.put(df.getLabel(), newValue);
            result.add(newValue);
        }

        if(getNetworkAdapter() != null) {
            sendData(networkData);
        }

        return result;
    }

    private void sendData(HashMap<String, E> data) {
        String dw = getDataWrapper().wrapData(this, data);
        NetworkAdapter networkAdapter = getNetworkAdapter();
        if(networkAdapter != null) {
            networkAdapter.send(dw);
        }
    }

    @Override
    public void addDataFeed(DataFeed<T> dataFeed) {
        this.dataFeed.add(dataFeed);
    }

    @Override
    public void removeDataFeed(DataFeed<T> dataFeed) {
        this.dataFeed.remove(dataFeed);
    }

    @Override
    public List<DataFeed<T>> getDataFeed() {
        return dataFeed;
    }

    public abstract void setDataConverter(DataConverter<E,T> dataConverter);

}
