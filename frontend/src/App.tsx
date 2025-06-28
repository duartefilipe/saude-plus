import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Button, Container, Box } from '@mui/material';
import { useState, useEffect } from 'react';
import './App.css';
import { login as apiLogin, getPatientByUserId, getBodyMetrics, getMealPlans, getTrainingPlans, getProfessionalNotes } from './api';

type Page = 'login' | 'register' | 'dashboard' | 'profile' | 'metrics' | 'mealplan' | 'workouts' | 'notes';

type User = {
  id: number;
  name: string;
  email: string;
  role: string;
};

type Metric = {
  id: number;
  date: string;
  weight: number;
  height: number;
  bmi: number;
};

type Meal = {
  id: number;
  name: string;
  time: string;
  items: string[];
};

type Workout = {
  id: number;
  name: string;
  exercises: string[];
};

type Note = {
  id: number;
  date: string;
  author: string;
  content: string;
};

function Dashboard() {
  return <Typography variant="h4">Dashboard</Typography>;
}
function Metrics() {
  return <Typography variant="h4">Métricas</Typography>;
}
function Nutrition() {
  return <Typography variant="h4">Nutrição</Typography>;
}
function Training() {
  return <Typography variant="h4">Treinos</Typography>;
}
function Content() {
  return <Typography variant="h4">Conteúdo</Typography>;
}

function App() {
  const [page, setPage] = useState<Page>('login');
  // Login states
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  // Register states
  const [regName, setRegName] = useState('');
  const [regEmail, setRegEmail] = useState('');
  const [regPassword, setRegPassword] = useState('');
  const [regRole, setRegRole] = useState('PATIENT');
  const [regLoading, setRegLoading] = useState(false);
  const [regError, setRegError] = useState('');
  const [regSuccess, setRegSuccess] = useState('');
  // Usuário autenticado
  const [user, setUser] = useState<User | null>(null);
  const [patientId, setPatientId] = useState<number | null>(null);

  // Estados para dados reais
  const [metrics, setMetrics] = useState<Metric[]>([]);
  const [metricsLoading, setMetricsLoading] = useState(false);
  const [metricsError, setMetricsError] = useState('');

  // Estados para plano alimentar
  const [meals, setMeals] = useState<Meal[]>([]);
  const [mealsLoading, setMealsLoading] = useState(false);
  const [mealsError, setMealsError] = useState('');

  // Estados para treinos
  const [workouts, setWorkouts] = useState<Workout[]>([]);
  const [workoutsLoading, setWorkoutsLoading] = useState(false);
  const [workoutsError, setWorkoutsError] = useState('');

  // Estados para notas profissionais
  const [notes, setNotes] = useState<Note[]>([]);
  const [notesLoading, setNotesLoading] = useState(false);
  const [notesError, setNotesError] = useState('');

  // Estados para perfil
  const [patient, setPatient] = useState<any>(null);
  const [patientLoading, setPatientLoading] = useState(false);
  const [patientError, setPatientError] = useState('');

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const data = await apiLogin(email, password);
      setSuccess('Login realizado com sucesso!');
      setError('');
      setUser({
        id: data.user.id,
        name: data.user.name,
        email: data.user.email,
        role: data.user.role
      });
      // Buscar patientId
      const patient = await getPatientByUserId(data.user.id);
      setPatientId(patient.id);
      setPage('dashboard');
    } catch (err: any) {
      setError(err.message || 'Erro ao fazer login');
      setSuccess('');
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setRegLoading(true);
    setRegError('');
    setRegSuccess('');
    try {
      const res = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: regName, email: regEmail, password: regPassword, role: regRole })
      });
      const data = await res.json();
      if (res.ok) {
        setRegSuccess('Cadastro realizado com sucesso! Faça login.');
        setRegError('');
      } else {
        setRegError(data.error || 'Erro ao cadastrar');
        setRegSuccess('');
      }
    } catch (err) {
      setRegError('Erro de conexão com o servidor');
      setRegSuccess('');
    } finally {
      setRegLoading(false);
    }
  };

  const handleLogout = () => {
    setUser(null);
    setPage('login');
    setEmail('');
    setPassword('');
    setSuccess('');
    setError('');
  };

  // Menu lateral
  const SideMenu = () => (
    <div style={{
      background: 'var(--color-bg-secondary)',
      minHeight: '100vh',
      width: 90,
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      padding: '32px 0',
      borderRight: '1px solid var(--color-border)',
      position: 'fixed',
      left: 0,
      top: 0,
      zIndex: 10
    }}>
      <div style={{
        width: 48,
        height: 48,
        borderRadius: '50%',
        background: 'var(--color-accent-gradient)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        fontWeight: 900,
        fontSize: 28,
        color: '#fff',
        marginBottom: 32,
        boxShadow: '0 2px 8px #0006'
      }}>
        S+
      </div>
      <button onClick={() => setPage('dashboard')} style={{
        background: page === 'dashboard' ? 'var(--color-accent)' : 'transparent',
        color: page === 'dashboard' ? '#fff' : 'var(--color-text-secondary)',
        border: 'none',
        borderRadius: 8,
        marginBottom: 16,
        width: 48,
        height: 48,
        fontSize: 22,
        cursor: 'pointer',
        transition: 'background 0.2s'
      }} title="Dashboard">
        <span role="img" aria-label="dashboard">🏠</span>
      </button>
      <button onClick={() => setPage('metrics')} style={{
        background: page === 'metrics' ? 'var(--color-accent)' : 'transparent',
        color: page === 'metrics' ? '#fff' : 'var(--color-text-secondary)',
        border: 'none',
        borderRadius: 8,
        marginBottom: 16,
        width: 48,
        height: 48,
        fontSize: 22,
        cursor: 'pointer',
        transition: 'background 0.2s'
      }} title="Métricas">
        <span role="img" aria-label="métricas">📊</span>
      </button>
      <button onClick={() => setPage('mealplan')} style={{
        background: page === 'mealplan' ? 'var(--color-accent)' : 'transparent',
        color: page === 'mealplan' ? '#fff' : 'var(--color-text-secondary)',
        border: 'none',
        borderRadius: 8,
        marginBottom: 16,
        width: 48,
        height: 48,
        fontSize: 22,
        cursor: 'pointer',
        transition: 'background 0.2s'
      }} title="Plano Alimentar">
        <span role="img" aria-label="plano alimentar">🍽️</span>
      </button>
      <button onClick={() => setPage('workouts')} style={{
        background: page === 'workouts' ? 'var(--color-accent)' : 'transparent',
        color: page === 'workouts' ? '#fff' : 'var(--color-text-secondary)',
        border: 'none',
        borderRadius: 8,
        marginBottom: 16,
        width: 48,
        height: 48,
        fontSize: 22,
        cursor: 'pointer',
        transition: 'background 0.2s'
      }} title="Treinos">
        <span role="img" aria-label="treinos">🏋️‍♂️</span>
      </button>
      <button onClick={() => setPage('notes')} style={{
        background: page === 'notes' ? 'var(--color-accent)' : 'transparent',
        color: page === 'notes' ? '#fff' : 'var(--color-text-secondary)',
        border: 'none',
        borderRadius: 8,
        marginBottom: 16,
        width: 48,
        height: 48,
        fontSize: 22,
        cursor: 'pointer',
        transition: 'background 0.2s'
      }} title="Notas Profissionais">
        <span role="img" aria-label="notas">📝</span>
      </button>
      <button onClick={() => setPage('profile')} style={{
        background: page === 'profile' ? 'var(--color-accent)' : 'transparent',
        color: page === 'profile' ? '#fff' : 'var(--color-text-secondary)',
        border: 'none',
        borderRadius: 8,
        marginBottom: 16,
        width: 48,
        height: 48,
        fontSize: 22,
        cursor: 'pointer',
        transition: 'background 0.2s'
      }} title="Perfil">
        <span role="img" aria-label="perfil">👤</span>
      </button>
      <button onClick={handleLogout} style={{
        background: 'transparent',
        color: 'var(--color-text-secondary)',
        border: 'none',
        borderRadius: 8,
        marginTop: 'auto',
        width: 48,
        height: 48,
        fontSize: 22,
        cursor: 'pointer',
        transition: 'background 0.2s'
      }} title="Sair">
        <span role="img" aria-label="sair">🚪</span>
      </button>
    </div>
  );

  // Cards do dashboard
  const DashboardCards = () => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 24 }}>
      <div className="card" style={{ background: 'var(--color-bg-secondary)', display: 'flex', alignItems: 'center', gap: 16, cursor: 'pointer' }} onClick={() => setPage('metrics')}>
        <div style={{ fontSize: 32, color: 'var(--color-accent)' }}>📊</div>
        <div>
          <div style={{ fontWeight: 600, fontSize: 18 }}>Minhas Métricas</div>
          <div style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Acompanhe sua evolução corporal</div>
        </div>
      </div>
      <div className="card" style={{ background: 'var(--color-bg-secondary)', display: 'flex', alignItems: 'center', gap: 16, cursor: 'pointer' }} onClick={() => setPage('mealplan')}>
        <div style={{ fontSize: 32, color: 'var(--color-accent)' }}>🍽️</div>
        <div>
          <div style={{ fontWeight: 600, fontSize: 18 }}>Plano Alimentar</div>
          <div style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Veja e siga seu plano de refeições</div>
        </div>
      </div>
      <div className="card" style={{ background: 'var(--color-bg-secondary)', display: 'flex', alignItems: 'center', gap: 16, cursor: 'pointer' }} onClick={() => setPage('workouts')}>
        <div style={{ fontSize: 32, color: 'var(--color-accent)' }}>🏋️‍♂️</div>
        <div>
          <div style={{ fontWeight: 600, fontSize: 18 }}>Treinos</div>
          <div style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Acesse seus treinos personalizados</div>
        </div>
      </div>
      <div className="card" style={{ background: 'var(--color-bg-secondary)', display: 'flex', alignItems: 'center', gap: 16, cursor: 'pointer' }} onClick={() => setPage('notes')}>
        <div style={{ fontSize: 32, color: 'var(--color-accent)' }}>📝</div>
        <div>
          <div style={{ fontWeight: 600, fontSize: 18 }}>Notas Profissionais</div>
          <div style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Veja orientações e anotações do seu profissional</div>
        </div>
      </div>
    </div>
  );

  // Página de treinos
  const WorkoutsPage = () => {
    useEffect(() => {
      if (!patientId) return;
      setWorkoutsLoading(true);
      setWorkoutsError('');
      getTrainingPlans(patientId)
        .then((plans) => {
          // Adaptar conforme o formato real retornado pelo backend
          const allWorkouts: Workout[] = plans.map((plan: any) => ({
            id: plan.id,
            name: plan.name || 'Treino',
            exercises: plan.exercises?.map((ex: any) => ex.name || 'Exercício') || []
          }));
          setWorkouts(allWorkouts);
        })
        .catch((err) => setWorkoutsError(err.message || 'Erro ao buscar treinos'))
        .finally(() => setWorkoutsLoading(false));
    }, [patientId]);

    if (workoutsLoading) return <div>Carregando treinos...</div>;
    if (workoutsError) return <div style={{color: 'red'}}>{workoutsError}</div>;
    if (!workouts.length) return <div>Nenhum treino encontrado.</div>;

    return (
      <div>
        <h2 style={{ color: 'var(--color-accent)', marginBottom: 24, textAlign: 'center' }}>Meus Treinos</h2>
        <div style={{ display: 'flex', flexDirection: 'column', gap: 18 }}>
          {workouts.map(workout => (
            <div key={workout.id} className="card" style={{ background: 'var(--color-bg-secondary)', padding: 18 }}>
              <div style={{ fontWeight: 600, fontSize: 17, marginBottom: 8 }}>{workout.name}</div>
              <ul style={{ margin: 0, paddingLeft: 18, color: 'var(--color-text-secondary)', fontSize: 15 }}>
                {workout.exercises.map((exercise, idx) => (
                  <li key={idx}>{exercise}</li>
                ))}
              </ul>
            </div>
          ))}
        </div>
        <div style={{ textAlign: 'center', marginTop: 24 }}>
          <button onClick={() => setPage('dashboard')}>Voltar ao Dashboard</button>
        </div>
      </div>
    );
  };

  // Página de notas profissionais
  const NotesPage = () => {
    useEffect(() => {
      if (!patientId) return;
      setNotesLoading(true);
      setNotesError('');
      getProfessionalNotes(patientId)
        .then((notesData) => {
          const allNotes: Note[] = notesData.map((n: any) => ({
            id: n.id,
            date: n.createdAt,
            author: n.professional?.name || 'Profissional',
            content: n.content
          }));
          setNotes(allNotes);
        })
        .catch((err) => setNotesError(err.message || 'Erro ao buscar notas'))
        .finally(() => setNotesLoading(false));
    }, [patientId]);

    if (notesLoading) return <div>Carregando notas...</div>;
    if (notesError) return <div style={{color: 'red'}}>{notesError}</div>;
    if (!notes.length) return <div>Nenhuma nota encontrada.</div>;

    return (
      <div>
        <h2 style={{ color: 'var(--color-accent)', marginBottom: 24, textAlign: 'center' }}>Notas Profissionais</h2>
        <div style={{ display: 'flex', flexDirection: 'column', gap: 18 }}>
          {notes.map(note => (
            <div key={note.id} className="card" style={{ background: 'var(--color-bg-secondary)', padding: 18 }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 6 }}>
                <span style={{ fontWeight: 600, fontSize: 15 }}>{note.author}</span>
                <span style={{ color: 'var(--color-accent)', fontSize: 14 }}>{note.date}</span>
              </div>
              <div style={{ color: 'var(--color-text-secondary)', fontSize: 15 }}>{note.content}</div>
            </div>
          ))}
        </div>
        <div style={{ textAlign: 'center', marginTop: 24 }}>
          <button onClick={() => setPage('dashboard')}>Voltar ao Dashboard</button>
        </div>
      </div>
    );
  };

  // Página de plano alimentar
  const MealPlanPage = () => {
    useEffect(() => {
      if (!patientId) return;
      setMealsLoading(true);
      setMealsError('');
      getMealPlans(patientId)
        .then((plans) => {
          // Adaptar conforme o formato real retornado pelo backend
          const allMeals: Meal[] = plans.map((plan: any) => ({
            id: plan.id,
            name: plan.name || 'Refeição',
            time: plan.mealTime || '12:00',
            items: plan.items?.map((item: any) => item.foodItem?.name || 'Alimento') || []
          }));
          setMeals(allMeals);
        })
        .catch((err) => setMealsError(err.message || 'Erro ao buscar plano alimentar'))
        .finally(() => setMealsLoading(false));
    }, [patientId]);

    if (mealsLoading) return <div>Carregando plano alimentar...</div>;
    if (mealsError) return <div style={{color: 'red'}}>{mealsError}</div>;
    if (!meals.length) return <div>Nenhum plano alimentar encontrado.</div>;

    return (
      <div>
        <h2 style={{ color: 'var(--color-accent)', marginBottom: 24, textAlign: 'center' }}>Plano Alimentar</h2>
        <div style={{ display: 'flex', flexDirection: 'column', gap: 18 }}>
          {meals.map(meal => (
            <div key={meal.id} className="card" style={{ background: 'var(--color-bg-secondary)', padding: 18 }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 6 }}>
                <span style={{ fontWeight: 600, fontSize: 17 }}>{meal.name}</span>
                <span style={{ color: 'var(--color-accent)', fontSize: 15 }}>{meal.time}</span>
              </div>
              <ul style={{ margin: 0, paddingLeft: 18, color: 'var(--color-text-secondary)', fontSize: 15 }}>
                {meal.items.map((item, idx) => (
                  <li key={idx}>{item}</li>
                ))}
              </ul>
            </div>
          ))}
        </div>
        <div style={{ textAlign: 'center', marginTop: 24 }}>
          <button onClick={() => setPage('dashboard')}>Voltar ao Dashboard</button>
        </div>
      </div>
    );
  };

  // Página de métricas corporais
  const MetricsPage = () => {
    // Buscar métricas ao entrar na página
    useEffect(() => {
      if (!patientId) return;
      setMetricsLoading(true);
      setMetricsError('');
      getBodyMetrics(patientId)
        .then((sessions) => {
          // Supondo que sessions é um array de sessões, cada uma com data e métricas
          // Aqui você pode adaptar conforme o formato real retornado pelo backend
          const allMetrics: Metric[] = sessions.map((s: any) => ({
            id: s.id,
            date: s.date,
            weight: s.weight,
            height: s.height,
            bmi: s.bmi
          }));
          setMetrics(allMetrics);
        })
        .catch((err) => setMetricsError(err.message || 'Erro ao buscar métricas'))
        .finally(() => setMetricsLoading(false));
    }, [patientId]);

    if (metricsLoading) return <div>Carregando métricas...</div>;
    if (metricsError) return <div style={{color: 'red'}}>{metricsError}</div>;
    if (!metrics.length) return <div>Nenhuma métrica encontrada.</div>;

    return (
      <div>
        <Typography variant="h4">Métricas Corporais</Typography>
        <ul>
          {metrics.map((m) => (
            <li key={m.id}>
              {m.date}: {m.weight}kg, {m.height}m, IMC: {m.bmi}
            </li>
          ))}
        </ul>
      </div>
    );
  };

  // Página de perfil (estrutura inicial)
  const ProfilePage = () => {
    useEffect(() => {
      if (!patientId) return;
      setPatientLoading(true);
      setPatientError('');
      getPatientByUserId(user?.id || 0)
        .then((patientData) => {
          setPatient(patientData);
        })
        .catch((err) => setPatientError(err.message || 'Erro ao buscar dados do paciente'))
        .finally(() => setPatientLoading(false));
    }, [patientId, user?.id]);

    if (patientLoading) return <div>Carregando perfil...</div>;
    if (patientError) return <div style={{color: 'red'}}>{patientError}</div>;

    return (
      <div style={{ textAlign: 'center', marginTop: 32 }}>
        <div style={{
          width: 80,
          height: 80,
          borderRadius: '50%',
          background: 'var(--color-bg-secondary)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          margin: '0 auto 16px',
          fontSize: 36,
          color: 'var(--color-accent)',
          fontWeight: 700
        }}>
          {user?.name.charAt(0).toUpperCase()}
        </div>
        <div style={{ fontSize: 22, fontWeight: 600 }}>{user?.name}</div>
        <div style={{ color: 'var(--color-text-secondary)', fontSize: 15 }}>{user?.email}</div>
        <div style={{ color: 'var(--color-accent)', fontSize: 15, marginTop: 4 }}>{user?.role === 'PATIENT' ? 'Paciente' : 'Profissional'}</div>
        {patient && (
          <div style={{ marginTop: 24, textAlign: 'left', background: 'var(--color-bg-secondary)', padding: 18, borderRadius: 8 }}>
            <h3 style={{ marginBottom: 12 }}>Dados do Paciente</h3>
            <p><strong>Data de Nascimento:</strong> {patient.birthDate}</p>
            <p><strong>Gênero:</strong> {patient.gender}</p>
            <p><strong>Telefone:</strong> {patient.phone}</p>
            <p><strong>Endereço:</strong> {patient.address}</p>
          </div>
        )}
        <div style={{ marginTop: 32, color: 'var(--color-text-secondary)' }}>
          <i>Em breve: edição de perfil, troca de senha, foto, etc.</i>
        </div>
      </div>
    );
  };

  // Layout principal autenticado
  const AuthenticatedLayout = () => (
    <div style={{ display: 'flex', minHeight: '100vh', width: '100vw', background: 'var(--color-bg)' }}>
      <SideMenu />
      <div style={{ marginLeft: 110, flex: 1, padding: '40px 0', display: 'flex', justifyContent: 'center', alignItems: 'flex-start' }}>
        <div style={{ width: 500, maxWidth: '95vw' }}>
          {page === 'dashboard' && <DashboardCards />}
          {page === 'metrics' && <MetricsPage />}
          {page === 'mealplan' && <MealPlanPage />}
          {page === 'workouts' && <WorkoutsPage />}
          {page === 'notes' && <NotesPage />}
          {page === 'profile' && <ProfilePage />}
        </div>
      </div>
    </div>
  );

  return (
    <Router>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            🏥 Saúde Plus
          </Typography>
          <Button color="inherit" component={Link} to="/dashboard">Dashboard</Button>
          <Button color="inherit" component={Link} to="/metrics">Métricas</Button>
          <Button color="inherit" component={Link} to="/nutrition">Nutrição</Button>
          <Button color="inherit" component={Link} to="/training">Treinos</Button>
          <Button color="inherit" component={Link} to="/content">Conteúdo</Button>
        </Toolbar>
      </AppBar>
      <Container sx={{ mt: 4 }}>
        <Routes>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/metrics" element={<Metrics />} />
          <Route path="/nutrition" element={<Nutrition />} />
          <Route path="/training" element={<Training />} />
          <Route path="/content" element={<Content />} />
          <Route path="*" element={<Dashboard />} />
        </Routes>
      </Container>
      <Box component="footer" sx={{ bgcolor: '#333', color: 'white', textAlign: 'center', py: 2, mt: 4 }}>
        <Typography variant="body2">&copy; 2024 Saúde Plus - Plataforma de Saúde Integrada</Typography>
      </Box>
      {user ? (
        <AuthenticatedLayout />
      ) : (
        <div style={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', background: 'var(--color-bg)' }}>
          <div className="card" style={{ width: 350, boxShadow: '0 4px 24px #0008' }}>
            <h1 style={{
              textAlign: 'center',
              fontFamily: 'Inter, Arial, Helvetica, sans-serif',
              fontWeight: 700,
              background: 'var(--color-accent-gradient)',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent',
              fontSize: 32,
              marginBottom: 32
            }}>
              Saúde<span style={{ fontWeight: 300 }}>Plus</span>
            </h1>
            {page === 'login' && (
              <>
                <form onSubmit={handleLogin}>
                  <div style={{ marginBottom: 18 }}>
                    <label style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>E-mail</label>
                    <input
                      type="email"
                      value={email}
                      onChange={e => setEmail(e.target.value)}
                      required
                      style={{ width: '100%', marginTop: 4 }}
                      autoFocus
                    />
                  </div>
                  <div style={{ marginBottom: 18 }}>
                    <label style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Senha</label>
                    <input
                      type="password"
                      value={password}
                      onChange={e => setPassword(e.target.value)}
                      required
                      style={{ width: '100%', marginTop: 4 }}
                    />
                  </div>
                  {error && <div style={{ color: 'red', marginBottom: 12 }}>{error}</div>}
                  {success && <div style={{ color: 'green', marginBottom: 12 }}>{success}</div>}
                  <button type="submit" style={{ width: '100%', background: 'var(--color-accent)', color: '#fff', padding: 10, border: 'none', borderRadius: 4, fontWeight: 600, fontSize: 16 }} disabled={loading}>
                    {loading ? 'Entrando...' : 'Entrar'}
                  </button>
                </form>
                <div style={{ marginTop: 18, textAlign: 'center' }}>
                  <span style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Não tem conta?</span>
                  <button style={{ marginLeft: 8, color: 'var(--color-accent)', background: 'none', border: 'none', cursor: 'pointer', fontSize: 14 }} onClick={() => setPage('register')}>Cadastre-se</button>
                </div>
              </>
            )}
            {page === 'register' && (
              <>
                <form onSubmit={handleRegister}>
                  <div style={{ marginBottom: 18 }}>
                    <label style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Nome</label>
                    <input
                      type="text"
                      value={regName}
                      onChange={e => setRegName(e.target.value)}
                      required
                      style={{ width: '100%', marginTop: 4 }}
                    />
                  </div>
                  <div style={{ marginBottom: 18 }}>
                    <label style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>E-mail</label>
                    <input
                      type="email"
                      value={regEmail}
                      onChange={e => setRegEmail(e.target.value)}
                      required
                      style={{ width: '100%', marginTop: 4 }}
                    />
                  </div>
                  <div style={{ marginBottom: 18 }}>
                    <label style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Senha</label>
                    <input
                      type="password"
                      value={regPassword}
                      onChange={e => setRegPassword(e.target.value)}
                      required
                      style={{ width: '100%', marginTop: 4 }}
                    />
                  </div>
                  <div style={{ marginBottom: 18 }}>
                    <label style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Tipo de Conta</label>
                    <select value={regRole} onChange={e => setRegRole(e.target.value)} style={{ width: '100%', marginTop: 4 }}>
                      <option value="PATIENT">Paciente</option>
                      <option value="PROFESSIONAL">Profissional</option>
                    </select>
                  </div>
                  {regError && <div style={{ color: 'red', marginBottom: 12 }}>{regError}</div>}
                  {regSuccess && <div style={{ color: 'green', marginBottom: 12 }}>{regSuccess}</div>}
                  <button type="submit" style={{ width: '100%', background: 'var(--color-accent)', color: '#fff', padding: 10, border: 'none', borderRadius: 4, fontWeight: 600, fontSize: 16 }} disabled={regLoading}>
                    {regLoading ? 'Cadastrando...' : 'Cadastrar'}
                  </button>
                </form>
                <div style={{ marginTop: 18, textAlign: 'center' }}>
                  <span style={{ color: 'var(--color-text-secondary)', fontSize: 14 }}>Já tem conta?</span>
                  <button style={{ marginLeft: 8, color: 'var(--color-accent)', background: 'none', border: 'none', cursor: 'pointer', fontSize: 14 }} onClick={() => setPage('login')}>Entrar</button>
                </div>
              </>
            )}
          </div>
        </div>
      )}
    </Router>
  );
}

export default App;
