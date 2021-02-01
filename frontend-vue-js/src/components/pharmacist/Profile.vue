<template>
  <div>
    <ModalRoot />
    <NavbarPharmacist></NavbarPharmacist>

    <h1 id="pharmacistprofile">Pharmacist profile</h1>

    <div class="separator"></div>
    <md-avatar id="avatar" class="md-avatar-icon md-primary">P</md-avatar>

    <div id="containerprofiledata">
      <p class="profiledata profiledataName">{{ name }}</p>
      <p class="profiledata">{{ surname }}</p>
      <p class="profiledata">{{ phoneNumber }}</p>
      <p class="profiledata">{{ location }}</p>
      <md-button id="editbutton" @click="openSignIn" class="md-fab md-plain">
        <md-icon id="editicon">edit</md-icon>
      </md-button>
    </div>

    <!--Same as before, just passing default value as second parameter-->
    <div class="text-center section" id="calendar">
      <v-calendar
        class="custom-calendar max-w-full"
        :masks="masks"
        :attributes="attributes"
        disable-page-swipe
        is-expanded
      >
        <template v-slot:day-content="{ day, attributes }">
          <div class="flex flex-col h-full z-10 overflow-hidden">
            <span class="day-label text-sm text-gray-900">{{ day.day }}</span>
            <div class="flex-grow overflow-y-auto overflow-x-auto">
              <p
                v-for="attr in attributes"
                :key="attr.key"
                class="text-xs leading-tight rounded-sm p-1 mt-0 mb-1"
                :class="attr.customData.class"
              >
                {{ attr.customData.title }}
              </p>
            </div>
          </div>
        </template>
      </v-calendar>
    </div>
  </div>
</template>

<script>
import { ModalBus } from "./eventBus";
import ModalRoot from "./ModalRoot";

import SignInForm from "./examples/SignInForm";

import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";

Vue.use(VueAxios, axios);

export default {
  name: "PharmacistLanding",
  components: {
    ModalRoot,
  },
  methods: {
    openSignIn() {
      ModalBus.$emit("open", {
        component: SignInForm,
        title: "New user",
        closeOnClick: false,
      });
    },
  },
  data() {
    const month = new Date().getMonth();
    const year = new Date().getFullYear();
    return {
      url: process.env.VUE_APP_APIUSERSURL,
      list: undefined,
      name: "",
      surname: "",
      phoneNumber: "",
      location: "",
      masks: {
        weekdays: "WWW",
      },
      attributes: [
        {
          key: 1,
          customData: {
            title: "Lunch with mom.",
            class: "bg-red-600 text-white",
          },
          dates: new Date(year, month, 1),
        },
        {
          key: 2,
          customData: {
            title: "Take Noah to basketball practice",
            class: "bg-blue-500 text-white",
          },
          dates: new Date(year, month, 2),
        },
        {
          key: 3,
          customData: {
            title: "Noah's basketball game.",
            class: "bg-blue-500 text-white",
          },
          dates: new Date(year, month, 5),
        },
        {
          key: 4,
          customData: {
            title: "Take car to the shop",
            class: "bg-indigo-500 text-white",
          },
          dates: new Date(year, month, 5),
        },
        {
          key: 4,
          customData: {
            title: "Meeting with new client.",
            class: "bg-teal-500 text-white",
          },
          dates: new Date(year, month, 7),
        },
        {
          key: 5,
          customData: {
            title: "Mia's gymnastics practice.",
            class: "bg-pink-500 text-white",
          },
          dates: new Date(year, month, 11),
        },
        {
          key: 6,
          customData: {
            title: "Cookout with friends.",
            class: "bg-orange-500 text-white",
          },
          dates: { months: 5, ordinalWeekdays: { 2: 1 } },
        },
        {
          key: 7,
          customData: {
            title: "Mia's gymnastics recital.",
            class: "bg-pink-500 text-white",
          },
          dates: new Date(year, month, 22),
        },
        {
          key: 8,
          customData: {
            title: "Visit great grandma.",
            class: "bg-red-600 text-white",
          },
          dates: new Date(year, month, 25),
        },
      ],
    };
  },
  mounted() {
    // Vue.axios.get(this.url).then((resp) => {
    //   this.list = resp.data;
    // });

    //Use data from local storage not from API req
    if (localStorage.name) {
      this.name = localStorage.name;
      this.surname = localStorage.surname;
      this.phoneNumber = localStorage.phoneNumber;
      this.location = localStorage.location;
    } //else send req to APi
  },
};
</script>

<style>
.content {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
#calendar {
  z-index: 1;
  width: 40%;
  margin-left: 50%;
  margin-top: -10%;
}
#avatar {
  z-index: -1;
  width: 80px;
  height: 80px;
  margin-top: 5%;
  margin-right: 80%;
}
.profiledata {
  color: white;
  font-size: 30px;
  padding-top: 20px;
}
.profiledataName {
  padding-top: 20px;
}
#containerprofiledata {
  width: 400px;
  height: 300px;
  margin-left: 12%;
  background-color: #448aff;
  border-radius: 5%;
}
#editbutton {
  margin-top: 20px;
  background-color: white;
}
#editicon {
  color: #448aff;
}
#pharmacistprofile {
  margin-top: 20px;
  color: #448aff;
  font-size: 40px;
}
</style>
<style lang="postcss" scoped>
::-webkit-scrollbar {
  width: 0px;
}
::-webkit-scrollbar-track {
  display: none;
}
/deep/ .custom-calendar.vc-container {
  --day-border: 1px solid #b8c2cc;
  --day-border-highlight: 1px solid #b8c2cc;
  --day-width: 90px;
  --day-height: 90px;
  --weekday-bg: #f8fafc;
  --weekday-border: 1px solid #eaeaea;
  border-radius: 0;
   z-index: -1;
  width: 100%;
  & .vc-header {
    background-color: #f1f5f8;
    padding: 10px 0;
  }
  & .vc-weeks {
    padding: 0;
  }
  & .vc-weekday {
    background-color: var(--weekday-bg);
    border-bottom: var(--weekday-border);
    border-top: var(--weekday-border);
    padding: 5px 0;
  }
  & .vc-day {
    padding: 0 5px 3px 5px;
    text-align: left;
    height: var(--day-height);
    min-width: var(--day-width);
    background-color: white;
    &.weekday-1,
    &.weekday-7 {
      background-color: #eff8ff;
    }
    &:not(.on-bottom) {
      border-bottom: var(--day-border);
      &.weekday-1 {
        border-bottom: var(--day-border-highlight);
      }
    }
    &:not(.on-right) {
      border-right: var(--day-border);
    }
  }
  & .vc-day-dots {
    margin-bottom: 5px;
  }
}
</style>
