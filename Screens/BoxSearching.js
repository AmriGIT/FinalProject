import axios from 'axios';
import React, {Component} from 'react';
import moment, {Moment} from 'moment';
import {
  ImageBackground,
  View,
  Text,
  StyleSheet,
  Keyboard,
  FlatList,
  Alert,
  
} from 'react-native';
import {Button, Input, ListItem, Icon} from 'react-native-elements';
import {SafeAreaProvider} from 'react-native-safe-area-context';

class BoxSearching extends Component {
  constructor(props) {
    super(props);
    this.state = {
      code: '',
      renderData: [],
      data: [],
      statusdata: false,
      viewForm: true,
      isLoading:false
    };
    this.keyboardDidShowListener = Keyboard.addListener(
      'keyboardDidShow',
      this.keyboardDidShow,
    );
    this.keyboardDidHideListener = Keyboard.addListener(
      'keyboardDidHide',
      this.keyboardDidHide,
    );
  }

  keyboardDidShow = () => this.setState({isKeyboardShow: true});
  keyboardDidHide = () => this.setState({isKeyboardShow: false});

  buttonSeacrh = async () => {
    const {code} = this.state;
    this.setState({
      isLoading : true
    })
    try {
      const res = await axios.get(
        'http://192.168.1.110:8080/api/masuk/' + code,
        {
          headers: {
            'Content-Type': 'application/json',
          },
        },
      );
      console.log('iki dispo', res.data);
      this.setState({
        renderData: res.data.disposisi,
        statusdata: true,
        viewForm: false,
      });
    } catch (err) {
      Alert.alert('Error', 'Kode Tidak Ditemukan', [
        {text: 'Ok', onPress: () => this.resetColumn()},
      ]);
      this.setState({
        renderData: [],
      });
    }
  };
  resetColumn = async () => {
    // this.textinput.clear();
    this.setState({
      statusdata: false,
      viewForm: true,
      renderData: [],
      code: '',
      isLoading:false
    });
  };
  componentWillUnmount() {
    this.keyboardDidShowListener.remove();
    this.keyboardDidHideListener.remove();
  }

  render() {
    console.log('Render Data', this.state.renderData);
    if (!this.state.viewForm) {
      return (
        <>
          <FlatList
            data={this.state.renderData}
            renderItem={({item, i}) => (
              <View style={{backgroundColor:"red"}}>
                <ListItem key={i} bottomDivider>
                  <Icon name="verified" color="green" />
                  <ListItem.Content>
                    <ListItem.Title>{item.tujuan}</ListItem.Title>
                    <ListItem.Subtitle>
                      {moment(item.tgl_dispo).format('YYYY-MM-DD')}
                    </ListItem.Subtitle>
                    <ListItem.Subtitle>{item.isidisposisi}</ListItem.Subtitle>
                  </ListItem.Content>
                  {/* <ListItem.Chevron /> */}
                </ListItem>
              </View>
            )}
          />
          <Button title="Clear" type="solid" onPress={this.resetColumn} />
        </>
      );
    } else {
      return (
        <SafeAreaProvider>
          <ImageBackground
            style={styles.img}
            source={require('../images/bg.jpg')}>
            <View
              style={{
                ...styles.container,
              }}>
              <Text style={styles.teks}>Tracking Kode Surat</Text>
              <Text>{this.state.code}</Text>
              <Input
                placeholder="Masukkan Kode Surat"
                onChangeText={code => this.setState({code})}
                style={styles.input}
                ref={input => {
                  this.textinput = input;
                }}
              />
              {this.state.isLoading?(
                <Button disabled title={this.state.isLoading?"Loading....":"Cari"} type="solid" onPress={this.buttonSeacrh} />
              ):(
                <Button title={this.state.isLoading?"Loading....":"Cari"} type="solid" onPress={this.buttonSeacrh} />
              )}
              {/* <View>{post}</View> */}
              <FlatList
                data={this.state.renderData}
                renderItem={({item}) => (
                  <View style={styles.userCard}>
                    <Text style={styles.item}>{`${item.tujuan} `} </Text>
                    <Text
                      style={styles.itempost}>{`${item.isidisposisi}`}</Text>
                  </View>
                )}
              />

              {this.state.statusdata ? (
                <Button title="Clear" type="solid" onPress={this.resetColumn} />
              ) : (
                <Text />
              )}
            </View>
          </ImageBackground>
        </SafeAreaProvider>
      );
    }
  }
}
const styles = StyleSheet.create({
  teks: {
    color: 'white',
    fontSize: 30,
  },
  userCard: {
    backgroundColor: '#2f3542',
    paddingVertical: 6,
    paddingHorizontal: 6,
    borderRadius: 10,
    marginTop: 10,
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  item: {
    padding: 10,
    fontSize: 16,
    height: 44,
    color: 'white',
    textAlign: 'center',
  },
  itempost: {
    padding: 10,
    fontSize: 12,
    color: 'white',
    textAlign: 'center',
    maxWidth: 100,
  },
  img: {
    flex: 1,
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  input: {
    height: 40,
    margin: 10,
    borderWidth: 0,
    padding: 7,
    color: 'white',
  },
});
export default BoxSearching;