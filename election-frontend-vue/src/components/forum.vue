<script>
import FooterComponent from './FooterComponent.vue'
import HeaderComponent from './HeaderComponent.vue'

export default {
  name: "forum",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      newMessage: '',
      messages: []
    };
  },
  methods: {
    sendMessage() {
      if (this.newMessage.trim() !== '') {
        this.messages.push({ id: Date.now(), text: this.newMessage });
        this.newMessage = '';
      }
    }
  }
}
</script>
<template>
  <div>
    <HeaderComponent />
    <div class="chat-container">
      <h1>Forum</h1>
      <p>Discussieer hier over de partijen.</p>
      <div class="chat-box">
        <div class="messages">
          <div v-for="message in messages" :key="message.id" class="message">
            {{ message.text }}
          </div>
        </div>
        <div class="input-container">
          <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="Type your message here..." />
          <button @click="sendMessage">Send</button>
        </div>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>
<style>
.chat-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.chat-box {
  width: 80%;
  max-width: 800px;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  background-color: #f9f9f9;
}

.messages {
  max-height: 400px;
  overflow-y: auto;
  margin-bottom: 20px;
}

.message {
  background-color: #e0e0e0;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 10px;
}

.input-container {
  display: flex;
  align-items: center;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-right: 10px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>