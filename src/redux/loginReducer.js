const defaultState = {
  statusLogin : false,
  token : ""
}
const loginReducer = (state = defaultState, action) =>{
  console.log ("login Reducer ", action.type)
  switch (action.type){
    case "LOGIN_OK" : 
    return {
      statusLogin : true,
      token : action.payload
    }
    default :
    return state

  }
}
export default loginReducer