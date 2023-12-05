import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from 'axios';
import { BarChart } from '../components/BarChart';

const UserDashboard = () => {
    const [selectedDate, setSelectedDate] = useState(null);
    const [allDevices, setAllDevices] = useState([]);
    const [chartData, setChartData] = useState(null);
    const [renderChart, setRenderChart] = useState(false);

    useEffect(() => {
        getDevices();
    }, []);

    const getDevices = async () => {
        try {
            const devicesFound = await axios.get(`http://localhost:8081/User/GetDevices/${localStorage.getItem('userId')}`);
            setAllDevices(devicesFound.data);
            console.log(devicesFound.data);
        } catch (err) {
            console.log(err);
        }
    };

    const onDeviceClicked = async (e, val) => {
        try {
            const monitoredData = await axios.get(`http://localhost:8085/GetMonitoredData/${val.id}`);
            console.log(monitoredData.data);

            // Filtrarea datelor în funcție de data selectată
            const filteredData = monitoredData.data.filter((item) => {
                const itemDate = new Date(item.timeStamp).toDateString();
                return selectedDate ? itemDate === selectedDate.toDateString() : true;
            });

            let label = filteredData.map((el) => el.timeStamp);
            let data = filteredData.map((el) => el.energyConsumption);

            setChartData({
                labels: label,
                datasets: [
                    {
                        label: 'Energy Consumption',
                        data: data,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(255, 205, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(201, 203, 207, 0.2)',
                        ],
                        borderColor: [
                            'rgb(255, 99, 132)',
                            'rgb(255, 159, 64)',
                            'rgb(255, 205, 86)',
                            'rgb(75, 192, 192)',
                            'rgb(54, 162, 235)',
                            'rgb(153, 102, 255)',
                            'rgb(201, 203, 207)',
                        ],
                        borderWidth: 1,
                    },
                ],
            });

            setRenderChart(true);
        } catch (err) {
            console.log(err);
        }
    };

    const tableOfDevices = () => {
        return (
            <table className="table">
                <thead>
                <tr className="table-tr">
                    <th className="table-th" style={{ width: '30%' }}>
                        <b>Description</b>
                    </th>
                    <th className="table-th" style={{ width: '40%' }}>
                        <b>Address</b>
                    </th>
                    <th className="table-th" style={{ width: '30%' }}>
                        <b>Max Energy Consumption</b>
                    </th>
                </tr>
                </thead>
                <tbody className="table-body">
                {allDevices?.map((val, key) => {
                    return (
                        <tr key={key} onClick={(e) => onDeviceClicked(e, val)}>
                            <td className="table-td" style={{ width: '30%' }}>{val.description}</td>
                            <td className="table-td" style={{ width: '40%' }}>{val.address}</td>
                            <td className="table-td" style={{ width: '30%' }}>{val.maximumEnergyConsumption}</td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        );
    };

    const devicesTable = () => {
        if (allDevices?.length) {
            return tableOfDevices();
        }
        return <div>There are no devices to display. Please go to Users page and assign to yourself a device.</div>;
    };

    const getBarChart = () => {
        return <BarChart chartData={chartData} />;
    };

    return (
        <div className="admin-device-page">
            <h2>You can see below your devices.</h2>
            <div className="datepicker-container">
                <DatePicker selected={selectedDate} onChange={date => setSelectedDate(date)} />
            </div>
            <div className="device-table">
                <div className="users-table">{devicesTable()}</div>
            </div>
            <div className="chart-div">
                <div className="chart">{renderChart && getBarChart()}</div>
            </div>
        </div>
    );
};

export default UserDashboard;
