#/ debug display result

[keyword]paketic {package:.*}=package {package}

[keyword]use informUser=import skuc.io.skuciocore.models.csm.Group; import skuc.io.functions.GroupObjectFilterer; import skuc.io.skuciocore.models.utilityClasses.KeyValue;import skuc.io.skuciocore.services.NotificationService; global NotificationService notificationService
[keyword]use dispatch=import skuc.io.skuciocore.models.events.kjar.EventOccured
[keyword]use activateContext=import skuc.io.skuciocore.models.events.kjar.ActivateContextById;import skuc.io.skuciocore.models.events.kjar.ActivateContextByName
[keyword]use deactivateContext=import skuc.io.skuciocore.models.events.kjar.DeactivateContextById;import skuc.io.skuciocore.models.events.kjar.DeactivateContextByName
[keyword]use most common imports=import skuc.io.skuciocore.models.csm.configuration.Context;import skuc.io.skuciocore.models.events.device.ValueReceived;import java.util.ArrayList
[keyword]use all=import skuc.io.skuciocore.models.csm.*; import skuc.io.skuciocore.models.csm.configuration.*; import skuc.io.skuciocore.models.csm.*

[when]{eventName:\[?\w*\]?} has occured=EventOccured(name == "{eventName}")
[when]\[{eventName:\w+}\]=template_start{eventName}template_end

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

[when]curr_{paramName:\w*}=ValueReceived(paramName == "{paramName}")
[when]- manje od ${definedParam:[\w_-]*}=value < ${definedParam}
[when]- manje od {definedParam:\[?[\w_-]*\]?}=value < {definedParam}
[when]- manje ili jednako sa ${definedParam:[\w_-]*}=value <= ${definedParam}
[when]- manje ili jednako sa {definedParam:\[?[\w_-]*\]?}=value <= {definedParam}
[when]- vece od ${definedParam:[\w_-]*}=value > ${definedParam}
[when]- vece od {definedParam:\[?[\w_-]*\]?}=value > {definedParam}
[when]- vece ili jednako sa ${definedParam:[\w_-]*}=value >= ${definedParam}
[when]- vece ili jednako sa {definedParam:\[?[\w_-]*\]?}=value >= {definedParam}
[when]- jednako sa ${definedParam:[\w_-]*}=value == ${definedParam}
[when]- jednako sa {definedParam:\[?[\w_-]*\]?}=value == {definedParam}
[when]- razlicito od ${definedParam:[\w_-]*}=value != ${definedParam}
[when]- razlicito od {definedParam:\[?[\w_-]*\]?}=value != {definedParam}

[when]\[{definedParam:[\w_-]+}\]=template_start{definedParam}template_end

[then]informUser\(\)=Group group = (Group) kcontext.getKieRuntime().getObjects(new GroupObjectFilterer()).iterator().next(); \n EventOccured informUserEvent = new EventOccured("InformUser", new KeyValue<String, String>("groupId", group.getId()));
[then]\s?with \"{param:[\w-_]*}\"\s?:\s?{value:.*}=informUserEvent.addParam(new KeyValue<String, String>("{param}", {value}));
[then]\s?\=\=\=>=notificationService.sendFrom(informUserEvent);

[then]sisaj{staDaSisam:\(.*\)}=System.out.println{staDaSisam}
[then]dispatch\({eventName:\[?\w*\]?}\)=insert(new EventOccured("{eventName}"))
[then]activateContext\({contextName:\[?\w*\]?}\)=insert(new ActivateContextByName("{contextName}"))
[then]deactivateContext\({contextName:\[?\w*\]?}\)=insert(new DeactivateContextByName("{contextName}"))
[then]\[{eventName:\w+}\]=template_start{eventName}template_end