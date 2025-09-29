
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

function contarNumeroCompetenciasCandidatos(){
    const hashListaCompetencias: Map<string, number> = new Map();

    listCompetenciasDisponiveis.forEach(comp => {
        hashListaCompetencias.set(comp, 0);
    })

    console.log(listCandidatos)

    for (const c of listCandidatos){
        const candidato = c as any;
        for(const cp of candidato.competencias){
            const competencia = cp as any;
            if(hashListaCompetencias.has(competencia)){
                const numAtualComp = hashListaCompetencias.get(competencia) ?? 0;
                hashListaCompetencias.set(competencia, numAtualComp + 1);
            }
        }
    }

    return hashListaCompetencias;
}