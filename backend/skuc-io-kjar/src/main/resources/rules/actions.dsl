#/ debug display result

[keyword]paketic {package:.*}=package {package}

[keyword]priority=salience

[keyword]use informUser=import skuc.io.skuciocore.models.csm.Group; import skuc.io.functions.GroupObjectFilterer; import skuc.io.skuciocore.models.utilityClasses.KeyValue;import skuc.io.skuciocore.services.NotificationService; global NotificationService notificationService
[keyword]use changeStatus=import skuc.io.skuciocore.models.events.device.StatusReceived;import skuc.io.skuciocore.services.NotificationService
[keyword]use dispatch=import skuc.io.skuciocore.models.events.kjar.EventOccured
[keyword]use activateContext=import skuc.io.skuciocore.models.events.kjar.ActivateContextById;import skuc.io.skuciocore.models.events.kjar.ActivateContextByName
[keyword]use deactivateContext=import skuc.io.skuciocore.models.events.kjar.DeactivateContextById;import skuc.io.skuciocore.models.events.kjar.DeactivateContextByName
[keyword]use most common imports=import skuc.io.skuciocore.models.csm.configuration.Context;import skuc.io.skuciocore.models.events.device.ValueReceived;import java.util.ArrayList

[keyword]in group=agenda-group

[when]def ${definedParam:[\w_-]*}\s?\=\s{eventName:\w*} has occured=${definedParam} : EventOccured(name == "{eventName}")
[when]{eventName:\w*} has occured=EventOccured(name == "{eventName}")
[when]def ${definedParam:[\w_-]*}\s?\=\s{eventName1:\w*} or {eventName2:\w*} have occured=${definedParam} : EventOccured(name == "{eventName1}" || name == "{eventName2}")
[when]{eventName1:\w*} or {eventName2:\w*} have occured=EventOccured(name == "{eventName1}" || name == "{eventName2}")
[when]{eventName:\w*} has not occured=not EventOccured(name == "{eventName}")

[when]are {query:\w*}\(\)=are{query}()
[when]is {query:\w*}\(\)=is{query}()
[when]has {query:\w*}\(\)=has{query}()
[when]aren't {query:\w*}\(\)=not are{query}()
[when]isn't {query:\w*}\(\)=not is{query}()
[when]hasn't {query:\w*}\(\)=not has{query}()

[when]def ${definedParam:[\w_-]*}\s?\=\s? getContextIfActive\({contextName:\w*}\)=${definedParam} : Context(name == "{contextName}")
[when]ensureContextNotActive\({contextName:\w*}\)=not Context(name == "{contextName}")
[when]def ${definedParam:[\w_-]*}\s?\=\s? getContextIfActive\({contextName1:\w*} or {contextName2:\w*}\)=exists(Context(name == "{contextName1}" || name == "{contextName2}")) \n $orContexts : ArrayList() from collect (Context(name == "{contextName1}" || name == "{contextName2}")) \n ${definedParam} : Context() from $orContexts.get(0)
[when]def ${definedParam:[\w_-]*}\s?\=\s? getAnyContextIfActive\(\)=exists(Context()) \n $allContexts : ArrayList() from collect (Context()) \n ${definedParam} : Context() from $allContexts.get(0)

[when]def ${definedParam:[\w_-]*}\s?\=\s?C\[${contextParam:[\w_-]*}\]\[max_{paramName:\w*}\]=${definedParam} : Float() from ${contextParam}.getMax("{paramName}")
[when]def ${definedParam:[\w_-]*}\s?\=\s?C\[${contextParam:[\w_-]*}\]\[min_{paramName:\w*}\]=${definedParam} : Float() from ${contextParam}.getMin("{paramName}")
[when]def ${definedParam:[\w_-]*}\s?\=\s?C\[${contextParam:[\w_-]*}\]\[expected_{paramName:\w*}\]=${definedParam} : String() from ${contextParam}.getStatus("{paramName}")

[when]def $curr_{paramName:\w*}=$curr_{paramName}: ValueReceived(paramName == "{paramName}")
[when]- manje od ${definedParam:[\w_-]*}=value < ${definedParam}
[when]- manje ili jednako sa ${definedParam:[\w_-]*}=value <= ${definedParam}
[when]- vece od ${definedParam:[\w_-]*}=value > ${definedParam}
[when]- vece ili jednako sa ${definedParam:[\w_-]*}=value >= ${definedParam}
[when]- jednako sa ${definedParam:[\w_-]*}=value == ${definedParam}
[when]- razlicito od ${definedParam:[\w_-]*}=value != ${definedParam}

[then]informUser\(\)=Group group = (Group) kcontext.getKieRuntime().getObjects(new GroupObjectFilterer()).iterator().next(); \n EventOccured informUserEvent = new EventOccured("InformUser", new KeyValue<String, String>("groupId", group.getId()));
[then]\s?with \"{param:[@\w\d-_]*}\"\s?:\s?{value:.*}=informUserEvent.addParam(new KeyValue<String, String>("{param}", {value}));
[then]\s?\=\=\=>=notificationService.sendFrom(informUserEvent);

[then]changeStatus\(\"{deviceType:[\w\d-_]*}\",\s?\"{value:[\w\d-_]*}\"\)=StatusReceived statusReceived = new StatusReceived("", "{deviceType}DeviceId", "{deviceType}", "{value}");\n ((NotificationService) kcontext.getKieRuntime().getGlobal("notificationService")).sendFrom(statusReceived); \n insert(statusReceived)

[then]sisaj{staDaSisam:\(.*\)}=System.out.println{staDaSisam}
[then]dispatch\({eventName:\w*}\)=insert(new EventOccured("{eventName}"))
[then]activateContext\({contextName:\w*}\)=insert(new ActivateContextByName("{contextName}"))
[then]deactivateContext\({contextName:\w*}\)=insert(new DeactivateContextByName("{contextName}"))
[then]removeValue\({paramName:$[\w_-]*}\)=delete({paramName})
[then]removeEvent\({paramName:$[\w_-]*}\)=delete({paramName})