import {
    BrowserRouter,
    createBrowserRouter, createRoutesFromElements, Route,
    RouterProvider, Routes
} from 'react-router-dom'
import './App.css'
import RootLayout from "./layouts/RootLayout.jsx";
import Profile from "./pages/Profile.jsx";
import React from "react";
import Create from "./pages/Create.jsx";
import Dashboard from "./pages/Dashboard.jsx";

const router = createBrowserRouter(
    createRoutesFromElements(
            <Route path="/" element={<RootLayout />}>
                <Route index element={<Dashboard />} />
                <Route path="create" element={<Create />} />
                <Route path="profile" element={<Profile />} />
            </Route>
    )
)

function App() {
  return (
      <RouterProvider router={router} />
  )
}

export default App
