<template>
  <v-app>
    <h1>{{ this.m }}</h1>
    <v-sheet tile height="54" class="d-flex">
      <v-btn icon class="ma-2" @click="$refs.calendar.prev()">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
      <v-select
        v-model="type"
        :items="types"
        dense
        outlined
        hide-details
        class="ma-2"
        label="type"
      ></v-select>
      <v-select
        v-model="mode"
        :items="modes"
        dense
        outlined
        hide-details
        label="event-overlap-mode"
        class="ma-2"
      ></v-select>
      <v-select
        v-model="weekday"
        :items="weekdays"
        dense
        outlined
        hide-details
        label="weekdays"
        class="ma-2"
      ></v-select>
      <v-spacer></v-spacer>
      <v-btn icon class="ma-2" @click="$refs.calendar.next()">
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </v-sheet>
    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        v-model="value"
        :weekdays="weekday"
        :type="type"
        :events="events"
        :event-overlap-mode="mode"
        :event-overlap-threshold="30"
        :event-color="getEventColor"
        @change="getEvents"
      ></v-calendar>
    </v-sheet>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    m: [],
    response: null,
    type: "month",
    types: ["month", "week", "day", "4day"],
    mode: "stack",
    modes: ["stack", "column"],
    weekday: [0, 1, 2, 3, 4, 5, 6],
    weekdays: [
      { text: "Sun - Sat", value: [0, 1, 2, 3, 4, 5, 6] },
      { text: "Mon - Sun", value: [1, 2, 3, 4, 5, 6, 0] },
      { text: "Mon - Fri", value: [1, 2, 3, 4, 5] },
      { text: "Mon, Wed, Fri", value: [1, 3, 5] },
    ],
    value: "",
    events: [],
    colors: [
      "blue",
      "indigo",
      // 'deep-purple',
      // 'cyan',
      // 'green',
      // 'orange',
      // 'grey darken-1'
    ],
    names: [
      "Pregled",
      "Holiday",
      // 'PTO',
      // 'Travel',
      // 'Event',
      // 'Birthday',
      // 'Conference',
      // 'Party'
    ],
  }),
  methods: {
    getEvents({ start, end }) {
      var m = [];
      this.axios
        .get("http://localhost:8081/api/consultations", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        })
        .then((resp) => {
          m = resp.data;
          this.m = m;
          console.log(this.m[0].patientName);
          const events = [];
          const min = new Date(`${start.date}T00:00:00`);
          const max = new Date(`${end.date}T23:59:59`);
          const days = (max.getTime() - min.getTime()) / 86400000;
          const eventCount = this.rnd(days, days + 20);
          for (let i = 0; i < eventCount; i++) {
            const allDay = this.rnd(0, 3) === 0;
            const firstTimestamp = this.rnd(min.getTime(), max.getTime());
            const first = new Date(firstTimestamp - (firstTimestamp % 900000));
            const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000;
            const second = new Date(first.getTime() + secondTimestamp);
            events.push({
              name: this.names[this.rnd(0, this.names.length - 1)],
              start: first,
              end: second,
              color: this.colors[this.rnd(0, this.colors.length - 1)],
              timed: !allDay,
            });
            events.push({
              name: m[0].patientName,
              start: new Date("2021-02-02T11:00:00Z"),
              end: new Date("2021-02-02T16:00:00Z"),
              color: "red",
              timed: 1,
            });
          }
          this.events = events;
        });
    },
    getEventColor(event) {
      return event.color;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
  },
};
</script>


