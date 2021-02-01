<template>
  <div>
    <NavbarPharmacist></NavbarPharmacist>

    <h1 id="pharmacistprofile">Pharmacist profile</h1>

    <div class="separator"></div>
    <md-avatar id="avatar" class="md-avatar-icon md-primary">P</md-avatar>

    <div>
      <div id="containerprofiledata">
        <p class="profiledata profiledataName">{{ name }}</p>
        <p class="profiledata">{{ surname }}</p>
        <p class="profiledata">{{ phoneNumber }}</p>
        <p class="profiledata">{{ location }}</p>
        <md-button
          id="editbutton"
          @click="showDialog = true"
          class="md-fab md-plain"
        >
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
            <div
              class="flex flex-col h-full z-10 overflow-hidden md-elevation-24"
            >
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

    <div>
      <md-dialog :md-active.sync="showDialog">
        <md-dialog-title>Change personal data</md-dialog-title>
        <md-field>
          <label>Name</label>
          <md-input v-model="name"></md-input>
        </md-field>
        <md-field>
          <label>Surname</label>
          <md-input v-model="surname"></md-input>
        </md-field>
        <md-field>
          <label>Phone number</label>
          <md-input v-model="phoneNumber"></md-input>
        </md-field>
        <md-field>
          <label>Location</label>
          <md-input v-model="location"></md-input>
        </md-field>
        <md-dialog-actions>
          <md-button class="md-primary" @click="showDialog = false"
            >Close</md-button
          >
          <md-button class="md-primary" @click="showDialog = false"
            >Save</md-button
          >
        </md-dialog-actions>
      </md-dialog>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";

Vue.use(VueAxios, axios);

export default {
  name: "PharmacistLanding",
  data() {
    const month = new Date().getMonth();
    const year = new Date().getFullYear();
    return {
      showDialog: false,
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
            title:
              "Take Noah to basketball practice practice practice practice practice practice practice practice",
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
    this.name = "Mark";
    this.surname = "Ivancevic";
    this.phoneNumber = "062 236 563 12";
    this.location = "Novi Sad, Puskinova 13";
  },
};
</script>

<style>
.custom-calendar.vc-container {
  --day-border: 5px solid #000000;
  --day-border-highlight: 1px solid #b8c2cc;
  --day-width: 90px;
  --day-height: 90px;
  --weekday-bg: #114579;
  --weekday-border: 1px solid #eaeaea;
  border-radius: 0;
  width: 100%;
}
.vc-header {
  background-color: #a9c8e0;
  padding: 30px 0;
}
.vc-weeks {
  padding: 0;
}
.vc-weekday {
  background-color: #637685;
  border-bottom: var(--weekday-border);
  border-top: var(--weekday-border);
  padding: 5px 0;
}
.vc-day {
  padding: 0 5px 3px 5px;
  text-align: left;
  height: var(--day-height);
  min-width: var(--day-width);
  background-color: white;
}
.vc-day-dots {
  margin-bottom: 5px;
}

#calendar {
  width: 50%;
  margin-left: 50%;
  margin-top: -22%;
}
#avatar {
  width: 80px;
  height: 80px;
  margin-top: 5%;
  margin-right: 80%;
}
.profiledata {
  color: white;
  font-size: 30px;
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
  background-color: white;
}
#editicon {
  color: #448aff;
}
#pharmacistprofile {
  color: #448aff;
  font-size: 40px;
}
</style>
<style lang="postcss" scoped>
::-webkit-scrollbar {
  width: 2px;
}
::-webkit-scrollbar-track {
  display: none;
}
/deep/ .custom-calendar.vc-container {
  --day-border: 5px solid #000000;
  --day-border-highlight: 1px solid #b8c2cc;
  --day-width: 90px;
  --day-height: 90px;
  --weekday-bg: #f8fafc;
  --weekday-border: 1px solid #eaeaea;
  border-radius: 0;
  width: 100%;
  & .vc-header {
    background-color: #f1f5f8;
    padding: 30px 0;
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
