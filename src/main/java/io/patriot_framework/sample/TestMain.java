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

package io.patriot_framework.sample;

import io.patriot_framework.generator.controll.CoapDeviceControlServer;

import java.net.SocketException;

public class TestMain {

    public static void main(String[] args) {
//        DataFeed df = new NormalDistributionDataFeed(18, 2);
//        NetworkAdapter na = new Rest("http://requestbin.fullcontact.com/wv2m0ywv");
//        Sensor temperature = new Thermometer("thermometer", df);
//        temperature.setNetworkAdapter(na);
//
//        DataFeed tf = new ConstantDataFeed(2000);
//        TimeSimulation simulation = new TimeSimulationImpl(tf, temperature);
//        simulation.simulate();

        CoapDeviceControlServer server = null;
        try {
            server = new CoapDeviceControlServer();
            server.addEndpoints();
            server.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
