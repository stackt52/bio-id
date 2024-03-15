import Grid from '@mui/material/Unstable_Grid2';
import Box from "@mui/material/Box";
import {defaults} from "chart.js/auto"
import {Bar, Doughnut, Line} from "react-chartjs-2";

defaults.maintainAspectRatio = false;
defaults.responsive = true;

defaults.plugins.title.display = true;
defaults.plugins.title.align = "start";
defaults.plugins.title.font.size = 15;
defaults.plugins.title.color = "black";

const barData = [
    {
        label: "A",
        value: 32
    },
    {
        label: "B",
        value: 45
    },
    {
        label: "C",
        value: 23
    }

]

const defaultTheme = {
    borderRadius: 4,
    colors: {
        text: {
            label: '#879399',
            help: '#737373',
        },
        primary: {
            base: '#FFA500',
            dark1: '#BC9501',
            dark2: '#7D6300',
            light1: '#FDE380',
            light2: '#FEF9E6',
            light3: '#fff3e0',
            light4: '#E9F6F9',
            light5: '#F3F8FA',
        },
        secondary: {
            base: '#444E7C',
            dark1: '#363E63',
            dark2: '#282E4A',
            dark3: '#1B1F31',
            light1: '#8E94B0',
            light2: '#B4B8CA',
            light3: '#D9DBE4',
            light4: '#ECEEF2',
            light5: '#F5F5F8',
        },
        grayscale: {
            base: '#666666',
            dark1: '#323232',
            dark2: '#000000',
            light1: '#B2B2B2',
            light2: '#E0E0E0',
            light3: '#F0F0F0',
            light4: '#F7F7F7',
            light5: '#FFFFFF',
        },
        error: {
            base: '#E04355',
            dark1: '#A7323F',
            dark2: '#6F212A',
            light1: '#EFA1AA',
            light2: '#FAEDEE',
        },
        warning: {
            base: '#FF7F44',
            dark1: '#BF5E33',
            dark2: '#7F3F21',
            light1: '#FEC0A1',
            light2: '#FFF2EC',
        },
        alert: {
            base: '#FCC700',
            dark1: '#BC9501',
            dark2: '#7D6300',
            light1: '#FDE380',
            light2: '#FEF9E6',
        },
        success: {
            base: '#5AC189',
            dark1: '#439066',
            dark2: '#2B6144',
            light1: '#ACE1C4',
            light2: '#EEF8F3',
        },
        info: {
            base: '#66BCFE',
            dark1: '#4D8CBE',
            dark2: '#315E7E',
            light1: '#B3DEFE',
            light2: '#EFF8FE',
        },
    },
    opacity: {
        light: '10%',
        mediumLight: '35%',
        mediumHeavy: '60%',
        heavy: '80%',
    },
    typography: {
        families: {
            sansSerif: `'Inter', Helvetica, Arial`,
            serif: `Georgia, 'Times New Roman', Times, serif`,
            monospace: `'Fira Code', 'Courier New', monospace`,
        },
        weights: {
            light: 200,
            normal: 400,
            medium: 500,
            bold: 600,
        },
        sizes: {
            xxs: 9,
            xs: 10,
            s: 12,
            m: 14,
            l: 16,
            xl: 21,
            xxl: 28,
        },
    },
    zIndex: {
        aboveDashboardCharts: 10,
        dropdown: 11,
        max: 3000,
    },
    transitionTiming: 0.3,
    gridUnit: 4,
    brandIconMaxWidth: 37,
};

const lineData = [
    {
        label: "Jan",
        enrolment: 300,
        search: 270
    },
    {
        label: "Feb",
        enrolment: 170,
        search: 458
    },
    {
        label: "Mar",
        enrolment: 557,
        search: 656
    },
    {
        label: "Apr",
        enrolment: 70,
        search: 307
    },
    {
        label: "May",
        enrolment: 270,
        search: 609
    },
    {
        label: "Jun",
        enrolment: 400,
        search: 501
    }
]

export default function Home() {
    return (
        <Grid container spacing={2}>
            <Grid xs={12}>
                <Box sx={{bgcolor: "white", p: "10px", height: (theme) => theme.spacing(40)}}>
                    <Line
                        data={{
                            labels: lineData.map((d) => d.label),
                            datasets: [
                                {
                                    label: "Enrolments",
                                    data: lineData.map((d) => d.enrolment),
                                    backgroundColor: "#064FF0",
                                    borderColor: "#064FF0"
                                },
                                {
                                    label: "Search",
                                    data: lineData.map((d) => d.search),
                                    backgroundColor: "#FF3030",
                                    borderColor: "#FF3030"
                                }
                            ]
                        }}
                        options={{
                            elements: {
                                line: {
                                    tension: 0.5
                                }
                            },
                            plugins: {
                                title: {
                                    text: "Enrolment / Search"
                                }
                            }
                        }}/>
                </Box>
            </Grid>
            <Grid xs={6}>
                <Box sx={{bgcolor: "white", p: "10px", height: (theme) => theme.spacing(40)}}>
                    <Bar
                        data={{
                            labels: barData.map((d) => d.label),
                            datasets: [
                                {
                                    label: "Count",
                                    data: barData.map((d) => d.value),
                                    backgroundColor: [
                                        "rgba(43,63,229,0.8)",
                                        "rgba(250,192,19,0.8)",
                                        "rgba(253,135,135,0.8)"
                                    ],
                                    borderRadius: 5
                                }
                            ]
                        }}
                        options={{
                            plugins: {
                                title: {
                                    text: "Enrolment by Age & Sex"
                                }
                            }
                        }}
                    />
                </Box>
            </Grid>
            <Grid xs={6}>
                <Box sx={{bgcolor: "white", p: '10px', height: (theme) => theme.spacing(40)}}>
                    <Doughnut
                        data={{
                            labels: barData.map((d) => d.label),
                            datasets: [
                                {
                                    label: "Count",
                                    data: barData.map((d) => d.value),
                                    backgroundColor: [
                                        "rgba(43,63,229,0.8)",
                                        "rgba(250,192,19,0.8)",
                                        "rgba(253,135,135,0.8)"
                                    ],
                                    borderRadius: 5
                                }
                            ]
                        }}
                        options={{
                            plugins: {
                                title: {
                                    text: "Enrolment by Sex"
                                }
                            }
                        }}
                    />
                </Box>
            </Grid>
        </Grid>
    )
}