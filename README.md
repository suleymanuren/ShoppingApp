<h1 align="center">ShoppingApp</h1>
<h3 align="center">Final homework of Pazarama's Bootcamp with Patika Dev</h3>

<h3 align="center">Whats the ShoppingApp?</h3>

As the name suggests, ShoppingApp is a simple app that is entirely based on shopping.
There are many products in the application that the user can purchase. To mention these, for example, clothes, jewelry, electronic product, etc.

<hr>
<h3 align="center">What technology used for app?</h3>


- For user data and product basket data we used Firebase. 

- For products data we used FakeStore API. 

<hr>
<p float="left">

<img src="https://github.com/suleymanuren/ShoppingApp/blob/main/ReadMeAssets/onboardLogin.gif"/>
<img src="https://github.com/suleymanuren/ShoppingApp/blob/main/ReadMeAssets/userLoginLogout.gif"/>
<img src="https://github.com/suleymanuren/ShoppingApp/blob/main/ReadMeAssets/productBasket.gif"/>
</p>

<hr>

<h3 align="center">Which libraries used in app?</h3>

- <b> Lifecycle = </b> Used this library for listen to user actions and control what kind of action our application will take against user actions.
- <b>Data Binding = Used this library for getting objects to binding from layouts. While using binding no need to FindViewById so we're writing less code and project looking clean
For use data binding add this <b>dataBinding {
    enabled = true
}</b> to gradle. And you have to write codes in the layout in XML
- <b>Navigation = Used this library navigate between to screens
- <b> Live data =</b> As the name suggests, the reason we use livedata is to listen to live data and use them on our screens. It is beneficial for us that there is no loss or inaccuracy in the data by always pulling up-to-date data.
- <b>Gson = </b> It is impossible or difficult to parse the api we are pulling as a model without using the gson library. That's why we use the gson library. For example if we use like that <b>
@SerializedName("name")</b> name matching name from api and we can use easily parsed data.
- <b>Shared Preferences = </b> We can save data at local side with shared preferences. For example user login, onboarding etc
- <b>Hilt = </b> By freeing dependencies on modules such as viewmodel and repository and injecting these modules where we need them, we use these tools as if we had created a module.

- <b>Lottie = </b> Used this library for using animated photos I mean gif. 

- <b>Retrofit = </b> Its the networking library helping to use GET, POST, DELETE. We used this app for networking
We used this library because printing image. Usually images in the API, storing like string method. In kotlin ImageView doesn't accept directly string value. So glide helping to convert process.
