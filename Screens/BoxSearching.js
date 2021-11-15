import axios from 'axios';
import React, {Component} from 'react';
import {
  ImageBackground,
  View,
  Text,
  StyleSheet,
  Keyboard,
  FlatList,
  Alert,
} from 'react-native';
import {Button, Input, ListItem} from 'react-native-elements';
import {SafeAreaProvider} from 'react-native-safe-area-context';

class BoxSearching extends Component {
  constructor(props) {
    super(props);
    this.state = {
      code: '',
      renderData: [],
      data: [],
      statusdata:false
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
    try {
      const res = await axios.get(
        'http://192.168.1.10:8080/api/masuk/' + code,
        {
          headers: {
            'Content-Type': 'application/json',
          },
        },
      );

      this.setState({
        renderData: res.data.disposisi,
        statusdata:true,
      });
      
    } catch (err) {
      return (
        Alert.alert("Error","Kode Tidak Ditemukan",[{ text: "Ok",onPress: ()=>this.resetColumn()}])
      )
    }
  };
  resetColumn =()=>{
    this.textinput.clear()
    this.setState({
      statusdata: false,
      renderData:[]
    })

  }
  componentWillUnmount() {
    this.keyboardDidShowListener.remove();
    this.keyboardDidHideListener.remove();
  }
  render() {
    console.log('Render Data', this.state.renderData);
    const post = this.state.renderData.map((post, i) => (
      <View>
        <Text key={i + 17}>{post.tujuan}</Text>
        <Text key={i + 17}>{post.isidisposisi}</Text>
        <Text key={i + 17}>{post.tgl_dispo}</Text>
      </View>
    ));
    if (this.state.renderData === []) {
      return <Text>Proses</Text>;
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
              <Input
                placeholder="Masukkan Kode Surat"
                onChangeText={code => this.setState({code})}
                style={styles.input}
                ref={input=>{this.textinput = input}}
              />
              <Button title="Cari" type="solid" onPress={this.buttonSeacrh} />
            {/* <View>{post}</View> */}
            <FlatList
              data={this.state.renderData}
              renderItem={({item}) => (
                <Text style={styles.item}>{item.tujuan}</Text>
                )}
                />
            {this.state.statusdata?(
              <Button title="Clear" type="solid" onPress={this.resetColumn}/>
              ): <Text/>}
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
  item: {
    padding: 10,
    fontSize: 20,
    height: 44,
    color: 'white',
    textAlign: 'center',
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
