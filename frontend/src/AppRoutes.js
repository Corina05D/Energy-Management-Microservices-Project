import { Counter } from "./components/Counter";
import { Home } from "./components/Home";
import Login from "./pages/Login";
import AdminDashboard from "./pages/AdminDashboard";
import UserDashboard from "./pages/UserDashboard";
import ProtectedComponentAdmin from "./components/ProtectedComponentAdmin";
import ProtectedComponentUser from "./components/ProtectedComponentUser";
import AdminPageDevices from "./pages/AdminPageDevices";

const AppRoutes = () => {
    return [
        {
            index: true,
            element: <Home />
        },
        {
            path: '/counter',
            element: <Counter />
        },
        {
            path: '/login',
            element: <Login />
        },
        {
            path: '/admin-page',
            element: <ProtectedComponentAdmin><AdminDashboard /></ProtectedComponentAdmin>
        },
        {
            path: '/user-dashboard',
            element: <ProtectedComponentUser><UserDashboard /></ProtectedComponentUser>
        },
        {
            path: '/admin-page-devices',
            element: <ProtectedComponentAdmin><AdminPageDevices /></ProtectedComponentAdmin>
        }
    ];
}

export default AppRoutes;