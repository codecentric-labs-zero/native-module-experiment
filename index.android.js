/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  NativeModules,
  DeviceEventEmitter
} from 'react-native';

const Gyroscope = NativeModules.Gyroscope

export default class NativeModuleExperiment extends Component {
  constructor(props) {
    super(props)
    this.state = { sensor: {}}
  }

  componentWillMount() {
    Gyroscope.start()
    DeviceEventEmitter.addListener('GRAVITY_SENSOR_CHANGED', event => {
      this.setState({sensor: event})
    })
  }

  componentWillUnmount() {
    Gyroscope.stop()
  }

  render() {
    return (
      <View style={styles.container}>
        <Text>
          X: {this.state.sensor.forceX}{'\n'}
          Y: {this.state.sensor.forceY}{'\n'}
          Z: {this.state.sensor.forceZ}{'\n'}
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('NativeModuleExperiment', () => NativeModuleExperiment);
