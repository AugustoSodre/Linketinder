import { Chart, registerables } from "chart.js";
import type {ChartConfiguration} from "chart.js"
import { listCandidatos, listCompetenciasDisponiveis } from "../storage/lists";

export function generateCandidatosCompetenciaGraphListener(){
    Chart.register(...registerables);

    const canva = document.getElementById('chart-candidatos-competencias') as HTMLCanvasElement;

    const hashListaCompetencias = contarNumeroCompetenciasCandidatos()

    const config: ChartConfiguration<"bar"> = {
    type: "bar",
    data: {
        labels: Array.from(hashListaCompetencias.keys()),
        datasets: [
        {
            data: Array.from(hashListaCompetencias.values()),
            backgroundColor: ["red", "green", "blue", "orange", "purple", "brown"],
        },
        ],
    },
    options: {
        plugins: {
        legend: { display: false },
        title: {
            display: true,
            text: "Competências X N° Candidatos",
            font: { size: 18 },
        },
        },
    },
    };

    new Chart(canva, config);


}

function contarNumeroCompetenciasCandidatos(): Map<string, number>{
    return new Map(listCompetenciasDisponiveis.map(competencia =>
        [competencia, listCandidatos.filter(c => c.competencias.includes(competencia)).length]))
}