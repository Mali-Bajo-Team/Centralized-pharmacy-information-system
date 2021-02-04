<template>
    <v-app>
        <v-row>
            <v-col md="2"> </v-col>
            <v-col>
                <h1 class="ml-n primary--text">Search patients</h1>
                <v-container fluid>
                    <v-data-iterator
                        :items="items"
                        :items-per-page.sync="itemsPerPage"
                        :page.sync="page"
                        :search="search"
                        :sort-by="sortBy.toLowerCase()"
                        :sort-desc="sortDesc"
                        hide-default-footer
                    >
                        <template v-slot:header>
                            <v-toolbar dark color="blue darken-3" class="mb-1 primary">
                                <v-text-field
                                    v-model="search"
                                    clearable
                                    flat
                                    solo-inverted
                                    hide-details
                                    prepend-inner-icon="mdi-magnify"
                                    label="Search by name and surname"
                                ></v-text-field>
                                <template v-if="$vuetify.breakpoint.mdAndUp">
                                    <v-spacer></v-spacer>
                                    <v-spacer></v-spacer>
                                    <v-btn-toggle v-model="sortDesc" mandatory>
                                        <v-btn
                                            large
                                            depressed
                                            color="primary"
                                            :value="false"
                                        >
                                            <v-icon>mdi-arrow-up</v-icon>
                                        </v-btn>
                                        <v-btn
                                            large
                                            depressed
                                            color="primary"
                                            :value="true"
                                        >
                                            <v-icon>mdi-arrow-down</v-icon>
                                        </v-btn>
                                    </v-btn-toggle>
                                </template>
                            </v-toolbar>
                        </template>

                        <template v-slot:default="props">
                            <v-row>
                                <v-col
                                    v-for="item in props.items"
                                    :key="item.name"
                                    cols="12"
                                    sm="6"
                                    md="4"
                                    lg="3"
                                >
                                    <v-card>
                                        <v-card-title
                                            class="subheading font-weight-bold"
                                        >
                                            {{ item.name }}
                                        </v-card-title>

                                        <v-divider></v-divider>

                                        <v-list dense>
                                            <v-list-item
                                                v-for="(key,
                                                index) in filteredKeys"
                                                :key="index"
                                            >
                                                <v-list-item-content
                                                    :class="{
                                                        'blue--text':
                                                            sortBy === key
                                                    }"
                                                >
                                                    {{ key }}:
                                                </v-list-item-content>
                                                <v-list-item-content
                                                    class="align-end"
                                                    :class="{
                                                        'blue--text':
                                                            sortBy === key
                                                    }"
                                                >
                                                    {{
                                                        item[key.toLowerCase()]
                                                    }}
                                                </v-list-item-content>
                                            </v-list-item>
                                        </v-list>
                                    </v-card>
                                </v-col>
                            </v-row>
                        </template>

                        <template v-slot:footer>
                            <v-row class="mt-2" align="center" justify="center">
                                <span class="grey--text">Items per page</span>
                                <v-menu offset-y>
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-btn
                                            dark
                                            text
                                            color="primary"
                                            class="ml-2"
                                            v-bind="attrs"
                                            v-on="on"
                                        >
                                            {{ itemsPerPage }}
                                            <v-icon>mdi-chevron-down</v-icon>
                                        </v-btn>
                                    </template>
                                    <v-list>
                                        <v-list-item
                                            v-for="(number,
                                            index) in itemsPerPageArray"
                                            :key="index"
                                            @click="updateItemsPerPage(number)"
                                        >
                                            <v-list-item-title>{{
                                                number
                                            }}</v-list-item-title>
                                        </v-list-item>
                                    </v-list>
                                </v-menu>

                                <v-spacer></v-spacer>

                                <span class="mr-4 grey--text">
                                    Page {{ page }} of {{ numberOfPages }}
                                </span>
                                <v-btn
                                    fab
                                    dark
                                    color=" primary darken-3"
                                    class="mr-1"
                                    @click="formerPage"
                                >
                                    <v-icon>mdi-chevron-left</v-icon>
                                </v-btn>
                                <v-btn
                                    fab
                                    dark
                                    color="primary darken-3"
                                    class="ml-1"
                                    @click="nextPage"
                                >
                                    <v-icon>mdi-chevron-right</v-icon>
                                </v-btn>
                            </v-row>
                        </template>
                    </v-data-iterator>
                </v-container>
            </v-col>

            <v-col md="2"> </v-col>
        </v-row>
    </v-app>
</template>

<script>
export default {
    data() {
        return {
            itemsPerPageArray: [4, 8, 12, 16, 20],
            search: '',
            filter: {},
            sortDesc: false,
            page: 1,
            itemsPerPage: 12,
            sortBy: 'name',
            keys: ['Name'],
            items: [
                {
                    name: 'Nemanja Pualić'
                },
                {
                    name: 'Mirloslav Perić'
                },
                {
                    name: 'Eric Tomphson'
                },
                {
                    name: 'Varvick Velicki'
                },
                {
                    name: 'Miodrag Mikic'
                },
                {
                    name: 'Jelly Yipilin'
                },
                {
                    name: 'Velimir Mikic'
                },
                {
                    name: 'Nenad Nenadovic'
                },
                {
                    name: 'Vladimir Velickovic'
                },
                {
                    name: 'Vladislav Maksimovic'
                },
                {
                    name: 'Ivana Miroslavovic'
                },
                {
                    name: 'Federer Radoslavovic'
                },
                {
                    name: 'Stanko Miroslavovic'
                },
                {
                    name: 'Janko Radoslavovic'
                },
                {
                    name: 'Federer Radoslavovi'
                },
                {
                    name: 'Stanko Miroslavovi'
                },
                {
                    name: 'Janko Radoslavovi'
                },
                {
                    name: 'Janko Radoslavovi '
                }
            ]
        }
    },
    computed: {
        numberOfPages() {
            return Math.ceil(this.items.length / this.itemsPerPage)
        },
        filteredKeys() {
            return this.keys.filter(key => key !== 'Name')
        }
    },
    methods: {
        nextPage() {
            if (this.page + 1 <= this.numberOfPages) this.page += 1
        },
        formerPage() {
            if (this.page - 1 >= 1) this.page -= 1
        },
        updateItemsPerPage(number) {
            this.itemsPerPage = number
        }
    }
}
</script>

<style scoped>
</style>