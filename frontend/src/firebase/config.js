import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
    apiKey: "AIzaSyAJE_Qg8Yu4S7VxWwhqaNpM03qNEqHG15c",
    authDomain: "cooking-e16cc.firebaseapp.com",
    projectId: "cooking-e16cc",
    storageBucket: "cooking-e16cc.firebasestorage.app",
    messagingSenderId: "918990385440",
    appId: "1:918990385440:web:2433a7f055df1a84832745",
    measurementId: "G-CBGL4ZCHYK"
  };

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);
const auth = getAuth(app);

export { db, auth };