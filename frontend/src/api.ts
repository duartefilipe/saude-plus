// Serviço centralizado de API para o backend Spring Boot
const API_URL = 'http://localhost:8080/api'; // Corrigido para incluir o context path '/api'

export async function login(email: string, password: string) {
  const res = await fetch(`${API_URL}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  if (!res.ok) throw new Error('Falha no login');
  return res.json();
}

export async function getUser(id: number) {
  const res = await fetch(`${API_URL}/users/${id}`);
  if (!res.ok) throw new Error('Usuário não encontrado');
  return res.json();
}

export async function getPatientByUserId(userId: number) {
  const res = await fetch(`${API_URL}/patients/user/${userId}`);
  if (!res.ok) throw new Error('Paciente não encontrado');
  return res.json();
}

export async function getBodyMetrics(patientId: number) {
  const res = await fetch(`${API_URL}/body-metrics/sessions/patient/${patientId}`);
  if (!res.ok) throw new Error('Erro ao buscar métricas corporais');
  return res.json();
}

export async function getMealPlans(patientId: number) {
  const res = await fetch(`${API_URL}/nutrition/meal-plans/patient/${patientId}`);
  if (!res.ok) throw new Error('Erro ao buscar planos alimentares');
  return res.json();
}

export async function getTrainingPlans(patientId: number) {
  const res = await fetch(`${API_URL}/training/plans/patient/${patientId}`);
  if (!res.ok) throw new Error('Erro ao buscar planos de treino');
  return res.json();
}

export async function getProfessionalNotes(patientId: number) {
  const res = await fetch(`${API_URL}/professional-notes/patient/${patientId}`);
  if (!res.ok) throw new Error('Erro ao buscar notas profissionais');
  return res.json();
} 