
$(document).ready(function () {

    $(function () {
        $.getJSON('DatosEstadisticasSanAndres.jsp?opcion=1', function (data) {
//$.getJSON('../cargarEstadisticas', function (data) {
            var datos = [];
            var CantidadTotal;
            for (var i = 0; i < data.length; i++) {
                var serie = new Array(data[i].CLV_DESCRIPCION, parseInt(data[i].CANTIDAD));
                CantidadTotal = data[i].CANTIDAD_TOTAL;
                datos.push(serie);
            }
            $('#graficaClaseVeh').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: 'Porcentaje de Censos por Clase de Vehiculo. Total Vehiculos: ' + CantidadTotal
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                        name: 'Porcentajes Censos',
                        colorByPoint: true,
                        data: datos
                    }]
            });
        });
    });
    $(function () {
        $.getJSON('DatosEstadisticasSanAndres.jsp?opcion=2', function (data) {
            var datos = [];
            var CantidadTotal;
            for (var i = 0; i < data.length; i++) {
                var serie = new Array(data[i].PUN_DESCRIPCION, parseInt(data[i].CANTIDAD));
                CantidadTotal = data[i].CANTIDAD_TOTAL;
                datos.push(serie);
            }
            $('#graficaPuntosAten').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: 'Porcentaje de Censos por Punto de Atencion. Total Censos: ' + CantidadTotal
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                        name: 'Porcentajes Censos',
                        colorByPoint: true,
                        data: datos
                    }]
            });
        });
    });


    $(function () {
        $.getJSON('DatosEstadisticasSanAndres.jsp?opcion=3', function (data) {
            //$.getJSON('../cargarEstadisticas', function (data) {
            var datos = [];
            var CantidadTotal;
            for (var i = 0; i < data.length; i++) {
                var serie = new Array(data[i].GENERO, parseInt(data[i].PORCENTAJE));
                CantidadTotal = data[i].CANTIDAD_TOTAL;
                datos.push(serie);
            }
            $('#graficaPersonasGenero').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'column'
                },
                title: {
                    text: 'Porcentaje de Personas censada por Genero. Total Personas: '+CantidadTotal
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Porcentaje personas censadas'
                    }

                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
                },

                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y:.1f}%'
                        }
                    }
                },

                series: [{
                        name: 'Personas Censadas',
                        colorByPoint: true,
                        data: datos
                    }]
            });
        });
    });


$(function () {
        $.getJSON('DatosEstadisticasSanAndres.jsp?opcion=4', function (data) {
            //$.getJSON('../cargarEstadisticas', function (data) {
            var datos = [];
            var CantidadTotal;
            for (var i = 0; i < data.length; i++) {
                var serie = new Array(data[i].DESCRIPCION, parseInt(data[i].PORCENTAJE));
                CantidadTotal = data[i].CANTIDAD_TOTAL;
                datos.push(serie);
            }
            $('#graficaPersonasLicencia').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'column'
                },
                title: {
                    text: 'Porcentaje de Personas censada Licencia. Total Personas: '+CantidadTotal
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Porcentaje Personas licencia conduccion'
                    }

                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
                },

                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y:.1f}%'
                        }
                    }
                },

                series: [{
                        name: 'Personas Censadas',
                        colorByPoint: true,
                        data: datos
                    }]
            });
        });
    });
    
    $(function () {
        $.getJSON('DatosEstadisticasSanAndres.jsp?opcion=5', function (data) {
            var datos = [];
            var CantidadTotal;
            for (var i = 0; i < data.length; i++) {
                var serie = new Array(data[i].DESCRIPCION, parseInt(data[i].PORCENTAJE));
                CantidadTotal = data[i].CANTIDAD_TOTAL;
                datos.push(serie);
            }
            $('#graficaVehiculosPlacas').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'column'
                },
                title: {
                    text: 'Porcentaje de Vehiculos censados con o sin placa. Total Vehiculos: '+CantidadTotal
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Porcentaje Vehiculos Placa'
                    }

                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
                },

                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y:.1f}%'
                        }
                    }
                },

                series: [{
                        name: 'Vehiculos Censados',
                        colorByPoint: true,
                        data: datos
                    }]
            });
        });
    });
    
     $(function () {
        $.getJSON('DatosEstadisticasSanAndres.jsp?opcion=6', function (data) {
            var datos = [];
            var CantidadTotal;
            for (var i = 0; i < data.length; i++) {
                var serie = new Array(data[i].DESCRIPCION, parseInt(data[i].CANTIDAD));
                CantidadTotal = data[i].CANTIDAD_TOTAL;
                datos.push(serie);
            }
            $('#graficaVehiculoSoat').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: 'Porcentaje de Vehiculos con Soat. Total Vehiculos: ' + CantidadTotal
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                        name: 'Porcentajes Censos',
                        colorByPoint: true,
                        data: datos
                    }]
            });
        });
    });


$(function () {
        $.getJSON('DatosEstadisticasSanAndres.jsp?opcion=7', function (data) {
            var datos = [];
            var CantidadTotal;
            for (var i = 0; i < data.length; i++) {
                var serie = new Array(data[i].DESCRIPCION, parseInt(data[i].CANTIDAD));
                CantidadTotal = data[i].CANTIDAD_TOTAL;
                datos.push(serie);
            }
            $('#graficaVehiculoTecno').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: 'Porcentaje de Vehiculos con Tecnomecanica. Total Vehiculos: ' + CantidadTotal
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                        name: 'Porcentajes Censos',
                        colorByPoint: true,
                        data: datos
                    }]
            });
        });
    });

    
});