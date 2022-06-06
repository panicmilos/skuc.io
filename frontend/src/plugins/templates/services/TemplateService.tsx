import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { CreateTemplate, Template } from "../models";

export const TEMPLATE_SERVICE_ID = 'TemplateService';

export const useTemplatesService = (groupId: string) => {
  
  const [templatesService, setTemplatesService] = useState(new TemplatesService(groupId));

  useEffect(() => {
    setTemplatesService(new TemplatesService(groupId));
  }, [groupId]);

  return [templatesService];
}

export class TemplatesService extends CrudService<Template, CreateTemplate> {

  constructor(groupId: string) {
    super(TEMPLATE_SERVICE_ID, `${BACKEND_API}/groups/${groupId}/templates`);
  }

}