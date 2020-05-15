$('document').ready(function () {

    //Фкнкция скроллинга страницы
    (function(){
        $(window).scroll(function () {
            // значение, на которое сместилась страница при прокрутке
            var top = $(document).scrollTop();
            $('.splash').css({
                //задаём позицию фонового изображения
                'background-position': '0px -'+(top/3).toFixed(2)+'px'
            });
        });
    })();

    //Преобразование Unix времени в удобочитаемый формат типа: мясяц - день - год : часы - минуты - секунды
    function convertUnixTime(unixtimestamp){
        // Months array
        var months_arr = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
        // Convert timestamp to milliseconds
        var date = new Date(unixtimestamp*1000);
        // Year
        var year = date.getFullYear();
        // Month
        var month = months_arr[date.getMonth()];
        // Day
        var day = date.getDate();
        // Hours
        var hours = date.getHours();
        // Minutes
        var minutes = "0" + date.getMinutes();
        // Seconds
        var seconds = "0" + date.getSeconds();
        // Display date time in MM-dd-yyyy h:m:s format
        var convDataTime = month+'-'+day+'-'+year+' '+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);

        return convDataTime;
    }

    //Извлечение графика по его url
    function getGraph(url) {
        $('#graph').attr('src', url).attr("alt", null);
        console.log(url);
    }

    function getHostGoups() {
        var url = '/last_data/hostgroup_ids'
        $.get(url,
            "hostgroup_id=" + hostgroup_id,
            function (data) {
                if (data.type === 'error') {
                    alert('error');
                    return(false);
                }
                else {
                    console.log(data);
                    $("#hosts_charts :first").attr("value", 0)
                        .text("Все");
                    $.each(data, function(key, value) {
                        $('#hosts_groups')
                            .append($("<option></option>")
                                .attr("value",value.hostid)
                                .text(value.name));
                    });
                    $('#hosts_charts').attr('disabled', false);
                }
            },
            "json"
        );
    }

    //Выбор узла на странице Последние данные
    $('#host_groups_data').on('change', function () {
        var hostgroup_id = $(this).val();
        if (hostgroup_id === '0') {
            $('#hosts_data').html('<option>- Выберите группу -</option>');
            $('#hosts_data').attr('disabled', true);
            return(false);
        }
        $('#hosts_data').attr('disabled', true);
        $('#hosts_data').html('<option>загрузка...</option>');

        var url = '/hosts/groupids';
        //var url = /js/items.json
        //var url = '/devices?' + 'items_id=' + item_id;
        //$(location).attr('href', url);
        $.get(url,
            "hostgroup_id=" + hostgroup_id,
            function (data) {
                if (data.type === 'error') {
                    alert('error');
                    return(false);
                }
                else {
                    console.log(data);
                    $("#hosts_data :first").attr("value", 0)
                        .text("Все");
                    $.each(data, function(key, value) {
                        $('#hosts_data')
                            .append($("<option></option>")
                                .attr("value",value.hostid)
                                .text(value.name));
                    });
                    $('#hosts_data').attr('disabled', false);
                }
            },
            "json"
        );
    });

    //Выбор элементов узла на странице Последние данные !!!!!!!!!!
    $('#hosts_data').on('change', function () {
        var host_id = $(this).val();

        var url = '/items';
        //var url = /js/items.json
        //var url = '/devices?' + 'items_id=' + item_id;
        //$(location).attr('href', url);
        $.get(url,
            "host_id=" + host_id,
            function (data) {
                if (data.type === 'error') {
                    alert('error');
                    return(false);
                }
                else {
                    console.log(data);
                    $("#hosts_data :first").attr("value", 0)
                        .text("Все");
                    var history = '';
                    $.each(data, function(key, value) {
                        url = "" + value.itemid;
                        history += '<tr>';
                        history += '<td>' + value.name + '</td>';
                        history += '<td>' + value.delay + '</td>';
                        history += '<td>' + value.history + '</td>';
                        history += '<td>' + value.trends + '</td>';
                        history += '<td>' + convertUnixTime(value.lastclock) + '</td>';
                        history += '<td>' + value.lastvalue + '</td>';
                        history += '<td>' + '<a href="/last-data/graph?item_id='+value.itemid+'">' + 'График' + '</a>' + '</td>';
                        history += '</tr>';
                    });
                    $('#last_data tbody').html(history);
                    //$('#itemids').attr('value', data[host_id])
                    cookie
                }
            },
            "json"
        );
    });

    //Выбор узла на странице График
    $('#host_groups_charts').on('change', function () {
        var hostgroup_id = $(this).val();
        if (hostgroup_id === '0') {
            $('#hosts').html('<option>- Выберите группу -</option>');
            $('#hosts').attr('disabled', true);
            return(false);
        }
        $('#hosts_charts').attr('disabled', true);
        $('#hosts_charts').html('<option>загрузка...</option>');

        var url = '/hosts/groupids';
        //var url = /js/items.json
        //var url = '/devices?' + 'items_id=' + item_id;
        //$(location).attr('href', url);
        $.get(url,
            "hostgroup_id=" + hostgroup_id,
            function (data) {
                if (data.type === 'error') {
                    alert('error');
                    return(false);
                }
                else {
                    console.log(data);
                    $("#hosts_charts :first").attr("value", 0)
                        .text("Все");
                    $.each(data, function(key, value) {
                        $('#hosts_charts')
                            .append($("<option></option>")
                                .attr("value",value.hostid)
                                .text(value.name));
                    });
                    $('#hosts_charts').attr('disabled', false);
                }
            },
            "json"
        );
    });

    //Выбор графика на странице График
    $('#hosts_charts').on('change', function () {
        var host_id = $(this).val();
        if (host_id === '0') {
            $('#charts').html('<option>- Выберите узел -</option>');
            $('#charts').attr('disabled', true);
            return(false);
        }
        $('#charts').attr('disabled', true);
        $('#charts').html('<option>загрузка...</option>');

        var url = '/charts/chart';
        //var url = /js/items.json
        //var url = '/devices?' + 'items_id=' + item_id;
        //$(location).attr('href', url);
        $.get(url,
            "host_id=" + host_id,
            function (data) {
                if (data.type === 'error') {
                    alert('error');
                    return(false);
                }
                else {
                    console.log(data);
                    $("charts :first").attr("value", 0)
                        .text("Не выбрано");
                    $.each(data, function(key, value) {
                        $('#charts')
                            .append($("<option></option>")
                                .attr("value",value.graphid)
                                .text(value.name));
                    });
                    $('#charts').attr('disabled', false);
                }
            },
            "json"
        );
    });

    //Вызов графика по url на странице График
    $('#charts').on('change', function () {
        var graph_id = $(this).val();

        var url = 'http://192.168.1.50/zabbix/chart2.php?graphid=' + graph_id + '&width=' + window.screen.width;
        getGraph(url);
        var getGraphId = setInterval(function() {getGraph(url)}, 10000);
        $('#charts').on('change', function () {
                clearInterval(getGraphId);
            });
    });

    //Выбор узла на странице Последние данные
    $('#hosts').on('change', function () {
        var host_id = $(this).val();
        if (host_id === '0') {
            $('#items').html('<option>- Выберите узел -</option>');
            $('#items').attr('disabled', true);
            return(false);
        }
        $('#items').attr('disabled', true);
        $('#items').html('<option>загрузка...</option>');

        var url = '/items';
        var img_url = '';
        //var url = /js/items.json
        //var url = '/devices?' + 'items_id=' + item_id;
        //$(location).attr('href', url);
        $.get(url,
            "host_id=" + host_id,
            function (data) {
                if (data.type === 'error') {
                    alert('error');
                    return(false);
                }
                else {
                    console.log(data);
                    $("#items :first").remove();
                    //var options = '';
                    $.each(data, function(key, value) {
                        $('#items')
                            .append($("<option></option>")
                                .attr("value",value.itemid)
                                .text(value.name));
                        //console.log(value.itemDiscovery);
                        //options += '<option value="' + $(this).attr("hostid") + '">' + $(this).attr(value.name) + '</option>';
                        //console.log(data);
                        //console.log(value.hostid);
                        //options += '<option value="' + $(this).attr('id') + '">' + $(this).attr('title') + '</option>';
                    });
                    //$('#items').html(options);
                    $('#items').attr('disabled', false);
                }
            },
            "json"
        );
    });

    /*    $('#items').on('change', function ()  {
            var item_id = $(this).val();
            var url = '/history?item_id=' + item_id;
            $(location).attr('href', url);
        });*/
    //Выбор элемента на странице Последние данные
    $('#items').on('change', function ()  {
        var item_id = $(this).val();
        var url = '/history';
        //var url2 = 'http://192.168.1.50/zabbix/chart2.php?graphid=796&period=3600&stime=20180222183215&isNow=1&profileIdx=web.graphs&profileIdx2=796&width=1228&sid=e51432e51b66bdda&screenid=&curtime=1519320824115';
        var url1 = 'http://192.168.1.50/zabbix/chart2.php?graphid=803&period=3600&stime=20180222164309&isNow=1&profileIdx=web.graphs&profileIdx2=803&width=1228';
        //var url1 = 'http://192.168.1.50/zabbix/chart6.php?graphid=823&width=1000&graphtype=2&show_3d=1';
        //var url1 = 'https://im0-tub-by.yandex.net/i?id=6679459a4eaefcc15328aa8b34f6ce86';
        //$('#graph').everyTime()

        //setInterval(getGraph(), 5000);
        function getHistory() {
            $.get(url,
                "item_id=" + item_id,
                function (data) {
                    if (data.type === 'error') {
                        alert('error');
                        return (false);
                    }
                    else {
                        console.log(data);
                        //var history = '';
                        var history = '<caption>Последние значения</caption>\n' +
                            '<tr>\n' +
                            '<th>Время</th>\n' +
                            '<th>Значение</th>\n' +
                            '</tr>';
                        $.each(data, function (key, value) {
                            history += '<tr>';
                            history += '<td>' + convertUnixTime(value.clock) + '</td>';
                            history += '<td>' + value.value + '</td>';
                            history += '</tr>';
                        });
                        //alert(data);
                        $('#history').html(history);
                    }
                },
                "json"
            );
        }
        getHistory();
        setInterval(function () {
            getHistory()
        }, 10000);
        getGraph(url1);
        setInterval(function () { getGraph(url1) }, 10000);
        //getGraph();
        //setInterval('getHistory();', 10000);
    });

    //History
    $('#action').on('change', function () {
        var item_id = $('#itemids').val();
        var history_id = $(this).val();
        if (history_id === 'showGraph') {
            $(location).attr('href', 'graph?item_id=' + item_id);
        } else if (history_id === 'showValues') {
            var val = 'showValues';
            //$("#action option[value=" + val + "]").attr('selected', 'true').text('последние 100');
            $(location).attr('href', 'last-value?item_id=' + item_id);
            //$("#action [value='showValues']").attr("selected", "selected");
            //console.log(history_id);
        }
    });
});



/*
jQuery('document').ready(function () {
    $('#hosts').on('change', function(){
        $('#hosts option').each(function () {
            $('#result').html(this.text);
        })
        //$('#items').html($('select option:selected').text());
    })
    /!*    var array_option = new Array();
    $('#city option').each(function () {
        array_option.push(this.text);
    });
    if ($('#city option :selected')) {
        //$('div#result').html($('#city option:selected').text());
        //$('#items').prepend('<option value="0">Добавить в самое начала Select</option>');
        $("#city :selected").val();
    }*!/
    //alert(array_option[3]);
    /!*for(var i=1; i < array_option.length; i++) {
        if ($(array_option[i])) {
            alert('Hi');
        }
    }*!/
    //$('div#result').html(array_option);
});*/
